package com.example.carmanagement.views.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carmanagement.databinding.ItemCarListBinding
import com.example.carmanagement.model.Car

class CarsAdapter : ListAdapter<Car, CarsAdapter.CarsViewHolder>(DiffCallback()) {

   inner class CarsViewHolder(private val binding: ItemCarListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {

                }
            }
        }

        fun bind(car: Car) {
            binding.apply {
                carBrand.text = car.brand
                carPlate.text = car.plate
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsViewHolder {
        val binding = ItemCarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car) = oldItem.carId == newItem.carId

        override fun areContentsTheSame(oldItem: Car, newItem: Car) = oldItem == newItem

    }

}