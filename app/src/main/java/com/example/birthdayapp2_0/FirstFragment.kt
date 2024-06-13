package com.example.birthdayapp2_0


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.birthdayapp2_0.databinding.FragmentFirstBinding
import com.example.birthdayapp2_0.models.MyAdapter
import com.example.birthdayapp2_0.models.PersonViewmodel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var userInteractionDetected = false

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val PersonViewmodel: PersonViewmodel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SuspiciousIndentation", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PersonViewmodel.personsLiveData.observe(viewLifecycleOwner) { persons ->


            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons == null) View.GONE else View.VISIBLE
            binding.swiperefresh.isClickable = true
            if (persons != null) {
                val adapter = MyAdapter(persons) { position ->
                    val action =
                        FirstFragmentDirections.actionFirstFragmentToEditFragment(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_EditFragment*/)
                }
                var colums = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    colums = 4

                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    colums = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, colums)
                binding.recyclerView.adapter = adapter

            }
        }

        val email = auth.currentUser?.email
        if (email != null) {
            PersonViewmodel.reload(email)
        }

        binding.swiperefresh.setOnRefreshListener {
            val user_id = FirebaseAuth.getInstance().currentUser?.email
            if (user_id == null) {
                binding.textviewMessage.text = "Nobody is signed in "

            } else
                PersonViewmodel.reload(user_id)
            binding.swiperefresh.isRefreshing = false
        }



        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> PersonViewmodel.sortByName()
                1 -> PersonViewmodel.sortByAge()
                2 -> PersonViewmodel.sortByBirthday()
            }
        }



        binding.buttonFilterName.setOnClickListener {
            val filter = binding.edittextFilterName.text.toString().trim()
            if (filter.isBlank()) {
                binding.edittextFilterName.error = " No Title"
                return@setOnClickListener
            }
            PersonViewmodel.filterByName(filter)
        }


        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            findNavController().navigate(R.id.action_FirstFragment_to_addFragment)
        }

        binding.textviewMessage.text = "Welcome" + auth.currentUser?.email

        binding.buttonLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}




