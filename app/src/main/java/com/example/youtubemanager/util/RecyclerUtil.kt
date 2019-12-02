package com.example.youtubemanager.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.onScrolledToEnd(onScrolled: ()->Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    val recyclerLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
                    recyclerLayoutManager?.findLastVisibleItemPosition()?.let {
                        if (it == recyclerLayoutManager.itemCount - 1) {
                            onScrolled()
                        }
                    }
                }
                RecyclerView.SCROLL_STATE_SETTLING -> {
                }
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                }
            }
            super.onScrollStateChanged(recyclerView, newState)
        }
    })
}

class RecyclerUtil{

}