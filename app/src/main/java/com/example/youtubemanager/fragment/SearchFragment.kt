package com.example.youtubemanager.fragment

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubemanager.R
import com.example.youtubemanager.adapter.SearchAdapter
import com.example.youtubemanager.databinding.FragmentSearchBinding
import com.example.youtubemanager.network.GoogleApiService
import com.example.youtubemanager.network.model.YoutubeSearchResponse
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var searchAdapter = SearchAdapter()
    private val youtubeService = GoogleApiService.getYoutubeService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchResultsView.apply{
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        searchQueryView.setOnEditorActionListener{view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                performSearch()
                true
            }
            false
        }

        searchButton.setOnClickListener {
            performSearch()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun performSearch(){
        val searchQuery = searchQueryView.text.toString()
        youtubeService.findVideo(searchQuery, 10, null).enqueue(object: Callback<YoutubeSearchResponse>{
            override fun onFailure(call: Call<YoutubeSearchResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<YoutubeSearchResponse>,
                response: Response<YoutubeSearchResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    response.body()!!.let{ response ->
                        searchAdapter.setItems(response.items)
                    }
                }
            }
        })
    }


}