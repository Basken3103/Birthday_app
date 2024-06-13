package com.example.birthdayapp2_0


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.birthdayapp2_0.databinding.FragmentSecondBinding
import com.google.firebase.auth.FirebaseAuth



class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /*
        val currentUser = auth.currentUser
        if(currentUser != null){
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        */

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
                        //updateUI(user)
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

                    } else {
                        Log.w("APPLE", "createUserWithEmail : failure", task.exception)
                        binding.textviewMessage.text =
                            "Authentication failed:" + task.exception?.message
                        //updateUI(null)
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
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("APPLE", "createEmailAndPassword :success")
                        val user = auth.currentUser
                        binding.buttonRegister.setOnClickListener {
                            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        }
                    } else {
                        Log.w("APPLE", "createEmailAndPassword:failure", task.exception)
                        binding.textviewMessage.text =
                            "Registration failed: " + task.exception?.message
                        //updateUI(null)
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}