package com.gilbertohdz.asteroidradar.ui

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gilbertohdz.asteroidradar.R
import com.gilbertohdz.asteroidradar.models.Asteroid
import com.gilbertohdz.asteroidradar.models.PictureOfDay
import com.gilbertohdz.asteroidradar.ui.main.AsteroidApiStatus
import com.gilbertohdz.asteroidradar.ui.main.MainAdapter
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

@BindingAdapter("asteroidItems")
fun bindAsteroidItems(recyclerView: RecyclerView, items: List<Asteroid>?) {
    items?.let {
        with(recyclerView.adapter as MainAdapter) {
            updateItems(it)
        }
    }
}

@BindingAdapter("progress")
fun bindProgress(progressBar: ProgressBar, status: AsteroidApiStatus?) {
    status?.let {
        progressBar.visibility = if (status == AsteroidApiStatus.LOADING) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("statusMessage")
fun bindProgress(constraintLayout: ConstraintLayout, status: AsteroidApiStatus) {
    if (status == AsteroidApiStatus.ERROR) {
        Snackbar.make (constraintLayout, "an error has been occurred, try again.", Snackbar.LENGTH_SHORT)
    }
}

@BindingAdapter("imageOfTheDay")
fun bindImageOfTheDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        Picasso.get().load(pictureOfDay.url).into(imageView);
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
