package com.example.youtubemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubemanager.R
import com.example.youtubemanager.databinding.SearchListElementBinding
import com.example.youtubemanager.model.Resource
import com.example.youtubemanager.model.ResourceThumbnail
import com.squareup.picasso.Picasso
import java.util.*

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    private var items = LinkedList<Resource>()

    fun setItems(items: Collection<Resource>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: Collection<Resource>){
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


    class SearchViewHolder(binding: SearchListElementBinding) : RecyclerView.ViewHolder(binding.root){
        val titleView = binding.title
        val thumbnailView = binding.thumbnail


        fun bind(item: Resource){
            titleView.text = item.snippet?.title
            item.snippet?.thumbnails?.get(ResourceThumbnail.TYPE_MEDIUM)?.let{
                Picasso.get().load(it.url).into(thumbnailView)
            }
        }
    }
}