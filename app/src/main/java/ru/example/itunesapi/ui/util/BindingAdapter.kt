package ru.example.itunesapi.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("app:url")
fun loadImage(view: ImageView?, url: String?) {
    Picasso.get()
        .load(url)
//        .resizeDimen(500, 500)
        .resize(500, 500)
        .centerCrop()
        .into(view)
}
