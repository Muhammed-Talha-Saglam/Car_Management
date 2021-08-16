package com.example.carmanagement.views.fragments.loginPage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentLoginBinding
import com.example.carmanagement.model.UserType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {


    private val loginViewModel: LoginViewModel by viewModels()
    lateinit var binding: FragmentLoginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        binding.loginButton.setOnClickListener {
            login()
        }

        loginViewModel.isLoggedIn.observe(viewLifecycleOwner, { isLoggedIn ->
            if(isLoggedIn)
                goToCarListPage()
        })
    }

    private fun login() {
        val email = binding.editTextEmail
        val passWord = binding.editTextPassword

        if (email.text.toString().isNotBlank() && passWord.text.toString().isNotBlank()) {
            loginViewModel.authenticateUser(
                email.text.toString(),
                passWord.text.toString(),
                requireContext()
            )
        }
    }

    private fun goToCarListPage() {

        /*
        If the user type is admin, go to "Car List" page, go to "Date Picker Page"
         */
        val action = when(loginViewModel.currentUser.value!!.userType) {
            UserType.STANDART -> LoginFragmentDirections.actionLoginFragmentToDatePickFragment(loginViewModel.currentUser.value!!)
            else -> LoginFragmentDirections.actionLoginFragmentToCarListFragment(loginViewModel.currentUser.value!!, 0,0)
        }

        findNavController().navigate(action)

    }

}