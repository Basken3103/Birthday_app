package com.example.birthdayapp2_0

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdayapp2_0.databinding.FragmentEditBinding
import com.example.birthdayapp2_0.models.PersonViewmodel
import com.google.firebase.auth.FirebaseAuth





class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val personViewModel: PersonViewmodel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val modifyFragmentArgs: EditFragmentArgs = EditFragmentArgs.fromBundle(bundle)
        val position = modifyFragmentArgs.position
        val person = personViewModel[position]

        if (person == null) {
            binding.textviewMessage.text = "No such Friend!"
            return
        }

        binding.Name.setText(person.name)
        binding.birthDate.setText(person.birthDate.toString())
        binding.birthMonth.setText(person.birthMonth.toString())
        binding.birthYear.setText(person.birthYear.toString())

        /*
        binding.email.setText(person.userId.toString())
        */


        binding.buttonDeleteFriend.setOnClickListener {
            val user_id = FirebaseAuth.getInstance().currentUser?.email
            if (user_id != null) {
                personViewModel.delete(person.id)
                personViewModel.reload(user_id)
            }
            findNavController().popBackStack()
        }

        binding.buttonPrevious.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_editFragment)
        }



        binding.buttonEditNewFriend.setOnClickListener {
            val name = binding.Name.text.toString().trim()
            if (name == null) {
                binding.Name.error = "Name field is Empty"
            }
            val birthYear = binding.birthYear.text.toString().trim().toInt()
            val birthMonth = binding.birthMonth.text.toString().trim().toInt()
            val birthDate = binding.birthDate.text.toString().trim().toInt()

            /*
            val email = binding.eMail.text.toString().trim()


            val updatePerson =
                Person(
                    birthDate,
                    birthMonth,
                    birthYear,
                    email,
                )
           */


            val user_id = FirebaseAuth.getInstance().currentUser?.email
            if (user_id != null) {
                personViewModel.reload(user_id)
            }
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}