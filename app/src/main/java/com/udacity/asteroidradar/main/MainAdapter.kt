package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.bindAsteroidStatusImage
import com.udacity.asteroidradar.databinding.AsteroiddailyitemMainBinding

class MainAdapter(
    var itemClick: (Asteroid) -> Unit
) : ListAdapter<Asteroid, MainAdapter.DetailsViewHolder>(WeatherDC()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return DetailsViewHolder.from(parent).apply {
            itemView.setOnClickListener {
                itemClick(getItem(adapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class DetailsViewHolder(
        private val binding: AsteroiddailyitemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Asteroid) {
            bindAsteroidStatusImage(binding.imageAllStatus, data.isPotentiallyHazardous)
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DetailsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroiddailyitemMainBinding.inflate(layoutInflater, parent, false)
                return DetailsViewHolder(binding)
            }
        }
    }

    private class WeatherDC : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(
            oldItem: Asteroid,
            newItem: Asteroid
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Asteroid,
            newItem: Asteroid
        ) = oldItem == newItem

    }
}