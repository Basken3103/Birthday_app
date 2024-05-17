package com.example.birthdayapp2_0


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdayapp2_0.databinding.FragmentAddBinding
import com.example.birthdayapp2_0.models.Person
import com.example.birthdayapp2_0.models.PersonViewmodel



class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val PersonViewmodel: PersonViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonName.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            if (name.isEmpty()) {
                binding.editTextName.error = "Name required"
                return@setOnClickListener
            }

            val birthday = binding.editTextBirthday.text.toString().trim()
            if (birthday.isEmpty()) {
                binding.editTextBirthmonth.error = "Birthday required"
                return@setOnClickListener
            }

            val birthmonth = binding.editTextBirthmonth.text.toString().trim()
            if (birthmonth.isEmpty()) {
                binding.editTextBirthmonth.error = "Birthmonth required"
                return@setOnClickListener
            }

            val birthyear = binding.editTextBirthyear.text.toString().trim()
            if (birthyear.isEmpty()) {
                binding.editTextBirthyear.error = "Birthyear required"
                return@setOnClickListener
            }


            val age = 27
            val email = "123@hotmail.com"
            val person = Person(name, age, birthday.toInt(), birthmonth.toInt(), birthyear.toInt(), email)
            PersonViewmodel.add(person)
            findNavController().popBackStack()

        }
    }
}




