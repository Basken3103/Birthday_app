package com.example.birthdayapp2_0


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val PersonViewmodel: PersonViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        PersonViewmodel.personsLiveData.observe(viewLifecycleOwner) { persons ->
            //Log.d("APPLE", "observer $persons")
            binding.progressbar.visibility = View.GONE
            binding.recycleView.visibility = if (persons == null) View.GONE else View.VISIBLE
            if (persons != null) {
                val adapter = MyAdapter(persons) { position ->
                    val action = FirstFragmentDirections.actionFirstFragmentToEditFragment(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_EditFragment*/)

                }
                // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }

                binding.recycleView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recycleView.adapter = adapter
            }
        }


        /*
        PersonViewmodel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage

        }
         */
        val email = auth.currentUser?.email
        if (email != null) {
            PersonViewmodel.reload(email)
        }

        binding.swiperefresh.setOnRefreshListener {
            val email = auth.currentUser?.email
            if (email != null) {
                PersonViewmodel.reload(email)
            }
            binding.swiperefresh.isRefreshing = false // TODO too early
        }

        PersonViewmodel.personsLiveData.observe(viewLifecycleOwner) { books ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, books)
            binding.spinnerSorting.adapter = adapter/*binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) { // reacts instantly: Much to quick.
                    val action =
                        FirstFragmentDirections.actionFirstFragmentToSecondFragment(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }*/
        }



        /*
        binding.spinnerSorting.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> PersonViewmodel.sortByName()
                1 -> PersonViewmodel.sortByAge()
                2 -> PersonViewmodel.sortByBirthday()
            }
        }
       */
        binding.swiperefresh.setOnClickListener {

            val filter = binding.edittextFilterName.text.toString().trim()
            if (filter.isBlank()) {
                binding.edittextFilterName.error = " No Title"
                return@setOnClickListener
            }
            PersonViewmodel.filterByName(filter)
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Navigation from Firstfragment to Editfragment", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            findNavController().navigate(R.id.action_FirstFragment_to_editFragment)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                Snackbar.make(binding.buttonLogout, "Settings..", Snackbar.LENGTH_LONG).show()
                return true
            }

            R.id.action_logout -> {
                Snackbar.make(binding.buttonLogout, "Logout..", Snackbar.LENGTH_LONG).show()
                auth.signOut()
                findNavController().navigate(R.id.action_FirstFragment_to_editFragment)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}



