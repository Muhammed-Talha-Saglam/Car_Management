package com.example.carmanagement.views.fragments.addCarPage

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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

    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddCarBinding.bind(view)

        binding.textTakePic.setOnClickListener {
            takePicture()
        }

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


    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("onActivityResult", "onActivityResult")
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("onActivityResult", "Inside if")
            val imageBitmap = data?.extras?.get("data") as Bitmap
            Log.d("bitmap", imageBitmap.toString())
            binding.carImage.setImageBitmap(imageBitmap)
            addVarViewModel.carImage.value = imageBitmap

        }
    }


}