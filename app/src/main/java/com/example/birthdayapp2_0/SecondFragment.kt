package com.example.birthdayapp2_0



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdayapp2_0.databinding.FragmentSecondBinding
import com.example.birthdayapp2_0.models.PersonViewmodel
import com.google.firebase.auth.FirebaseAuth



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val PersonViewmodel: PersonViewmodel by activityViewModels()

    /*
    private fun activityViewModels(): ReadOnlyProperty<SecondFragment, PersonViewmodel> {

    }
     */

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "No e_mail"
                return@setOnClickListener
            }

            val password = binding.edittextPassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.edittextPassword.error = "No Password"
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("APPLE", "createUserWithEmail : success")
                        val user = auth.currentUser
                        val userEmail = user?.email
                        if(userEmail != null){
                            PersonViewmodel.setUserEmail(userEmail)
                            val savedEmail = userEmail
                            PersonViewmodel.getPersonById(savedEmail)
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

                        }
                        else{
                            Log.e("APPLE","User Email is empty or null")
                        }

                    } else {
                        Log.w("APPLE", "signInWithEmailAndPassword: failure", task.exception)
                        binding.textviewMessage.text = "Authentication failed: ${task.exception?.message}"
                    }
                }
        }

        binding.buttonRegister.setOnClickListener {
            val email = binding.edittextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "No Email"
                return@setOnClickListener
            }
            val password = binding.edittextPassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.edittextPassword.error = "No Password"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("APPEL", "createEmailAndPassword :success")
                        //val user = auth.currentUser
                        binding.buttonRegister.setOnClickListener {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        }
                    } else {
                        Log.w("APPEL", "createEmailAndPassword:failure", task.exception)
                        binding.textviewMessage.text =
                            "Registration failed: " + task.exception?.message
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}