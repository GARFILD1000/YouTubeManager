package com.example.youtubemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubemanager.R
import com.example.youtubemanager.databinding.SearchListElementBinding
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.model.ResourceThumbnail
import com.squareup.picasso.Picasso
import java.util.*

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    private var items = LinkedList<SearchResource>()
    var onItemClick: (SearchResource)->Unit = {}


    fun setItems(items: Collection<SearchResource>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: Collection<SearchResource>){
        val sizeBeforeAdding = this.items.size
        this.items.addAll(items)
        val sizeAfterAdding = this.items.size
        notifyItemRangeInserted(sizeBeforeAdding, sizeAfterAdding - 1)
    }

    fun removeItems(){
        val sizeBeforeRemoving = this.items.size
        if (sizeBeforeRemoving > 0) {
            this.items.clear()
            notifyItemRangeRemoved(0, sizeBeforeRemoving-1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SearchListElementBinding = DataBindingUtil
            .inflate(inflater, R.layout.search_list_element, parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        items.getOrNull(position)?.let{
            holder.bind(it)
        }
    }


    inner class SearchViewHolder(binding: SearchListElementBinding) : RecyclerView.ViewHolder(binding.root){
        val titleView = binding.title
        val thumbnailView = binding.thumbnail


        fun bind(item: SearchResource){
            titleView.text = item.snippet?.title
            item.snippet?.thumbnails?.get(ResourceThumbnail.TYPE_MEDIUM)?.let{
                Picasso.get().load(it.url).into(thumbnailView)
            }
            titleView.setOnClickListener{
                if (adapterPosition != RecyclerView.NO_POSITION){
                    onItemClick(item)
                }
            }
            thumbnailView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION){
                    onItemClick(item)
                }
            }
        }
    }
}