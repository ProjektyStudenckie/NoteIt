package com.sww.noteit.view_model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sww.noteit.R
import com.sww.noteit.model.Photo

class PhotosListAdapter(
    private var photosList: MutableList<Photo>,
    private var onClickListener: OnClickListener
): RecyclerView.Adapter<PhotosListAdapter.PhotosHolder>() {

    inner class PhotosHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.one_row_photos_list, parent, false
        )

        return PhotosHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
        val photo = photosList[position]
        var image = holder.itemView.findViewById<ImageView>(R.id.imageView)

        image.setImageBitmap(photo.image)

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, photo)
        }
    }

    override fun getItemCount() = photosList.size


    internal fun setPhotos(photos: MutableList<Photo>) {
        this.photosList = photos
        notifyDataSetChanged()
    }

    internal fun getPhotos(): MutableList<Photo> {
        return photosList
    }

    fun removeAt(position: Int) {
        photosList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Photo)
    }
}