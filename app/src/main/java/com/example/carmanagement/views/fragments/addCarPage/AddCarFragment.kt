package com.example.carmanagement.views.fragments.addCarPage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentAddCarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCarFragment : Fragment(R.layout.fragment_add_car) {

    private val addVarViewModel: AddCarViewModel by viewModels()
    lateinit var binding: FragmentAddCarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddCarBinding.bind(view)

        binding.buttonSave.setOnClickListener {
            saveCar()

        }
        binding.buttonCancel.setOnClickListener {
            goToCarListPage()
        }

    }

    private fun goToCarListPage() {
        findNavController().popBackStack()
    }

    private fun saveCar() {
        val brand = binding.editTextBrand.text.toString().trim()
        val plate = binding.editTextPlate.text.toString().trim()

        if (brand.isNotBlank() && plate.isNotBlank()) {
            addVarViewModel.addNewCar(brand, plate)

            Toast.makeText(
                requireContext(),
                getString(R.string.new_car_added),
                Toast.LENGTH_LONG
            ).show()

            goToCarListPage()
        }
    }


}