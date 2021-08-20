package com.example.carmanagement.views.fragments.userHistoryPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carmanagement.constants.toStringDateAndTime
import com.example.carmanagement.databinding.ItemUserHistoryBinding
import com.example.carmanagement.model.UserHistory

class UserHistoryAdapter : ListAdapter<UserHistory, UserHistoryAdapter.UserHistoryViewHolder>(
    DiffCallback()
) {

    inner class UserHistoryViewHolder(private val binding: ItemUserHistoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(userHistory: UserHistory) {

            binding.apply {
                carBrand.text = userHistory.brand
                carPlate.text = userHistory.plate
                startDate.text = userHistory.reservationStart.toStringDateAndTime()
                endDate.text = userHistory.reservationsEnd.toStringDateAndTime()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHistoryViewHolder {
        val binding = ItemUserHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHistoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<UserHistory>() {
        override fun areItemsTheSame(oldItem: UserHistory, newItem: UserHistory) = oldItem.userHistoryId == newItem.userHistoryId

        override fun areContentsTheSame(oldItem: UserHistory, newItem: UserHistory) = oldItem == newItem

    }

}