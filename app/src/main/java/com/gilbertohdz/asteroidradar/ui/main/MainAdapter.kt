package com.gilbertohdz.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gilbertohdz.asteroidradar.R
import com.gilbertohdz.asteroidradar.databinding.ItemAsteroidBinding
import com.gilbertohdz.asteroidradar.models.Asteroid

class MainAdapter(
    private val viewModel: MainViewModel
): RecyclerView.Adapter<MainViewHolder>() {

    private val asteroids = mutableListOf<Asteroid>()

    private fun ViewGroup.inflate(
        layoutResId: Int
    ): View = LayoutInflater.from(this.context).inflate(layoutResId, this, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(parent.inflate(R.layout.item_asteroid))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindTo(viewModel, asteroids[position])
    }

    override fun getItemCount() = asteroids.size

    fun updateItems(items: List<Asteroid>) {
        asteroids.clear()
        asteroids.addAll(items)
        notifyDataSetChanged()
    }
}

class MainViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    private val binding = ItemAsteroidBinding.bind(itemView)

    fun bindTo(viewModel: MainViewModel,
               asteroid: Asteroid) {
        binding.asteroid = asteroid
    }
}