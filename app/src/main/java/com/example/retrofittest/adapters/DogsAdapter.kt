package com.example.retrofittest.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofittest.R

class DogsAdapter(private val listPhotos: ArrayList<String>) :
    RecyclerView.Adapter<DogsAdapter.PhotoHolder>() {

    inner class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var photo: ImageView = itemView.findViewById(R.id.iv_dog_image)

        fun loadPhoto(url: String) {

            Glide.with(itemView)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(photo)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_holder, parent, false)
        return PhotoHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listPhotos.size
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.loadPhoto(listPhotos[position])
    }
}