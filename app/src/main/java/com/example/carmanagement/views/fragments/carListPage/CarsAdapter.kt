package com.example.carmanagement.views.fragments.carListPage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carmanagement.databinding.ItemCarListBinding
import com.example.carmanagement.model.Car
import com.example.carmanagement.model.UserType

class CarsAdapter(
    private val listener: OnItemClickListener,
    private val userType: UserType
    ) : ListAdapter<Car, CarsAdapter.CarsViewHolder>(DiffCallback()) {

   inner class CarsViewHolder(private val binding: ItemCarListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        Log.d("item",getItem(position).toString() )
                        listener.onItemClick(getItem(position))
                    }
                }
            }
        }

        fun bind(car: Car) {
            binding.apply {
                carBrand.text = car.brand
                carPlate.text = car.plate
                /*
                Admin cannot reserve car for himself/herself
                 */
                if (userType == UserType.ADMIN){
                    textClickToReserve.visibility = View.GONE
                }
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

    interface OnItemClickListener {
        fun onItemClick(car: Car)
    }

}