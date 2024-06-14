package com.example.androidexam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidexam.R
import com.example.androidexam.data.local.PersonEntity

/**
 * Created by Ivan Esguerra on 6/13/2024.
 **/
class PersonAdapter(diffCallback: DiffUtil.ItemCallback<PersonEntity>) :
    PagingDataAdapter<PersonEntity, PersonAdapter.PersonViewHolder>(diffCallback) {

    var onItemClick: ((PersonEntity) -> Unit) ?= null

    object PersonComparator : DiffUtil.ItemCallback<PersonEntity>() {
        override fun areItemsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PersonEntity, newItem:PersonEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        (holder as? PersonViewHolder)?.bind(item = getItem(position)!!, onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder.getInstance(parent)
    }

    class PersonViewHolder(private val view: View ) : RecyclerView.ViewHolder(view) {
        companion object {
            //get instance of the PersonViewHolder
            fun getInstance(parent: ViewGroup): PersonViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_layout, parent, false)
                return PersonViewHolder(view)
            }
        }

        var personImage: ImageView = view.findViewById(R.id.image_iv)
        var personNameTv: TextView = view.findViewById(R.id.person_name_tv)
        var personAgeTv: TextView = view.findViewById(R.id.person_age_tv)

        fun bind(item: PersonEntity, onItemClick: ((PersonEntity) -> Unit) ?= null) {
            val defaultOptions = RequestOptions().error(R.drawable.ic_launcher_background)
            Glide.with(itemView.context).setDefaultRequestOptions(defaultOptions)
                .load(item.picture.large)
                .centerCrop()
                .into(personImage)
            personNameTv.text = "Name: ${item.name.first} ${item.name.last}"
            personAgeTv.text = "Age: ${item.dob.age}"
            view.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}