package com.example.carmanagement.views.fragments.carListPage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentCarListBinding
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.User
import com.example.carmanagement.model.UserType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_car_list.*
import java.time.LocalDateTime
import java.time.ZoneOffset


@AndroidEntryPoint
class CarListFragment : Fragment(R.layout.fragment_car_list), CarsAdapter.OnItemClickListener {

    lateinit var binding: FragmentCarListBinding

    private val carListViewModel: CarListViewModel by viewModels()

    lateinit var alertDialog: AlertDialog.Builder
    lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = arguments?.getParcelable<User>("user")!!
        carListViewModel.currentUser.value = user

        val startDate = arguments?.getLong("startDate")!!
        carListViewModel.startLocalDateTime.value =
            LocalDateTime.ofEpochSecond(startDate, 0, ZoneOffset.UTC)

        val endDate = arguments?.getLong("endDate")!!
        carListViewModel.endLocalDateTime.value =
            LocalDateTime.ofEpochSecond(endDate, 0, ZoneOffset.UTC)


        binding = FragmentCarListBinding.bind(view)

        val carAdapter = CarsAdapter(this, user.userType)


        binding.apply {
            rvCarList.apply {
                adapter = carAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ic_logout.setOnClickListener {
                logOut()
            }
            addCarButton.setOnClickListener {
                goToAddCarPage()
            }
            userHistoryButton.setOnClickListener {
                goToUserHistoryPage()
            }
        }

        user.userType.let { userType ->

            // Standard user cannot add a new car.
            if (userType == UserType.STANDART) {
                binding.addCarButton.visibility = View.GONE
            }
            //Admin doesn't have a reservation history.
            else if (userType == UserType.ADMIN) {
                binding.userHistoryButton.visibility = View.GONE
            }

        }

        /*
        If user type is "Admin", list all the cars
        else if user type is "Standard", list only the available cars
        according to the start and end dates
         */
        val cars = when (user.userType) {
            UserType.ADMIN -> carListViewModel.allCars
            else -> carListViewModel.availableCars
        }

        cars.observe(viewLifecycleOwner) { carList ->
            Log.d("availableCars", carList.size.toString())
            carAdapter.submitList(carList)
        }

        if (user.userType == UserType.STANDART)
            carListViewModel.getAvailableCars()
    }

    private fun goToUserHistoryPage() {

        val action =
            CarListFragmentDirections.actionCarListFragmentToUserHistoryFragment(user.userId)
        findNavController().navigate(action)
    }


    override fun onItemClick(car: Car) {

        /*
        Admin cannot reserve car for himself/herself
        */
        if (user.userType == UserType.ADMIN)
            return

        alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.setMessage(getString(R.string.alert_message))
        alertDialog.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, _ ->

            carListViewModel.makeNewReservation(
                brand = car.brand,
                plate = car.plate,
                carId = car.carId,
                requireContext()
            )

            dialog.dismiss()
        }
            .show()
    }

    private fun goToAddCarPage() {
        val action = CarListFragmentDirections.actionCarListFragmentToAddCarFragment()
        findNavController().navigate(action)
    }

    private fun logOut() {
        val action = CarListFragmentDirections.actionCarListFragmentToLoginFragment()
        findNavController().navigate(action)
    }

}