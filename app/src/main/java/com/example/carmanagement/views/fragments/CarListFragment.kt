package com.example.carmanagement.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentCarListBinding
import com.example.carmanagement.viewModels.MainViewModel
import com.example.carmanagement.views.fragments.adapters.CarsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_car_list.*


@AndroidEntryPoint
class CarListFragment : Fragment(R.layout.fragment_car_list) {

    lateinit var binding: FragmentCarListBinding

    private val viewModel: MainViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentCarListBinding.bind(view)

        val carAdapter = CarsAdapter()

        binding.apply {
            rvCarList.apply {
                adapter = carAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ic_logout.setOnClickListener {
                logOut()
            }
        }

        viewModel.availableCars.observe(viewLifecycleOwner) { carList ->
            Log.d("availableCars", carList.size.toString())
            carAdapter.submitList(carList)
        }



    }

    private fun logOut() {
        viewModel.logOut()
        val action = CarListFragmentDirections.actionCarListFragmentToLoginFragment()
        findNavController().navigate(action)
    }

}