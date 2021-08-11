package com.example.carmanagement.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentLoginBinding
import com.example.carmanagement.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {


    private val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentLoginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        binding.loginButton.setOnClickListener {
            login()


        }

        viewModel.isLoggedIn.observe(viewLifecycleOwner, { isLoggedIn ->
            if (isLoggedIn)
                goToCarListPage()
        })
    }

    private fun login() {
        val email = binding.editTextEmail
        val passWord = binding.editTextPassword

        if (email.text.toString().isNotBlank() && passWord.text.toString().isNotBlank()) {
            viewModel.authenticateUser(
                email.text.toString(),
                passWord.text.toString(),
                requireContext()
            )

        }

    }

    private fun goToCarListPage() {
        val action =
            com.example.carmanagement.views.fragments.LoginFragmentDirections.actionLoginFragmentToCarListFragment()
        findNavController().navigate(action)
    }

}