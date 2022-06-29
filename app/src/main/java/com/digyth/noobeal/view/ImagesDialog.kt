package com.digyth.noobeal.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.digyth.noobeal.R
import com.digyth.noobeal.databinding.SelectorImagesItemBinding

class ImagesDialog private constructor(context: Context) : AlertDialog.Builder(context) {

    companion object {

        /**
         * show a dialog with images
         *
         * @param context context
         * @param title title of dialog
         * @param urls urls of the images to show
         * @param listener listener to call when an option is clicked
         */
        fun show(context: Context, title: String, urls: Array<String>, listener: Listener) {
            val dialog = ImagesDialog(context)
            dialog.setTitle(title)
            val view =
                LayoutInflater.from(context)
                    .inflate(R.layout.selector_images_dialog, null)
            view.findViewById<RecyclerView>(R.id.image_list).apply {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                adapter = Adapter(context, urls)
            }
            dialog.setView(view)
            dialog.setNegativeButton("Cancel") { _, _ ->
                listener.onCancel()
            }
            dialog.setNeutralButton("Expand") { _, _ ->
                listener.onExpand()
            }
            dialog.setCancelable(false)
            dialog.show()
        }
    }

    class Adapter(private val context: Context, private val data: Array<String>) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

        class ViewHolder(val binding: SelectorImagesItemBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                SelectorImagesItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit =
            with(holder.binding) {
                Glide.with(context)
                    .load(data[position])
                    .placeholder(R.drawable.ic_image)
                    .into(image)
                image.setOnClickListener {
                    Toast.makeText(context, data[position], Toast.LENGTH_SHORT).show()
                }
            }

        override fun getItemCount(): Int {
            return data.size
        }

    }

    interface Listener {
        fun onCancel()
        fun onExpand()
    }

}