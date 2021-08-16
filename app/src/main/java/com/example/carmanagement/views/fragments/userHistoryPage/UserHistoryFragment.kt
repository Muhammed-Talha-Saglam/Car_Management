package com.example.carmanagement.views.fragments.userHistoryPage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carmanagement.R
import com.example.carmanagement.databinding.FragmentUserHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_history.*


@AndroidEntryPoint
class UserHistoryFragment : Fragment(R.layout.fragment_user_history) {

    private val userHistoryViewModel: UserHistoryViewModel by viewModels()

    lateinit var binding: FragmentUserHistoryBinding
    var userId: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserHistoryBinding.bind(view)

        userId = arguments?.getInt("userId")!!

        val userHistoryAdapter = UserHistoryAdapter()

        binding.apply {
            rv_user_history.apply {
                adapter = userHistoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            buttonClearHistory.setOnClickListener {
                userHistoryViewModel.clearUserHistory(userId)
            }

            ic_back.setOnClickListener {
                goBack()
            }
        }

        userHistoryViewModel.getUserHistory(userId)


        userHistoryViewModel.userHistory.observe(viewLifecycleOwner) {
            Log.d("userHistory" , it.toString())
            userHistoryAdapter.submitList(it)
        }



    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}