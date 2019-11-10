package com.example.youtubemanager.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubemanager.R
import com.example.youtubemanager.activity.MainActivity
import com.example.youtubemanager.adapter.SearchAdapter
import com.example.youtubemanager.databinding.FragmentSearchBinding
import com.example.youtubemanager.model.Resource
import com.example.youtubemanager.model.SearchResource
import com.example.youtubemanager.network.GoogleApiService
import com.example.youtubemanager.network.model.YoutubeResponse
import com.example.youtubemanager.viewmodel.YoutubeChannelViewModel
import com.example.youtubemanager.viewmodel.YoutubePlayerViewModel
import com.example.youtubemanager.viewmodel.YoutubeSearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchFragment : Fragment() {
    private val LOG_TAG = "SearchFragment"
    private lateinit var binding: FragmentSearchBinding
    private var searchAdapter = SearchAdapter().apply {
        onItemClick = { resource ->
            Log.d(LOG_TAG, "Kind : ${resource.kind}")
            when (resource.id!!.kind){
                Resource.KIND_YOUTUBE_VIDEO -> {
                    playerViewModel.cuddentVideoLiveData.postValue(resource)
                    (context as MainActivity).goWatchVideo()
                }
                Resource.KIND_YOUTUBE_CHANNEL -> {
                    channelViewModel.currentChannelLiveData.postValue(resource)
                    (context as MainActivity).goToChannel()
                }
            }

        }
    }
    private val youtubeService = GoogleApiService.getYoutubeService()
    private lateinit var playerViewModel: YoutubePlayerViewModel
    private lateinit var channelViewModel: YoutubeChannelViewModel
    private lateinit var searchViewModel: YoutubeSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playerViewModel = ViewModelProviders.of(activity!!).get(YoutubePlayerViewModel::class.java)
        channelViewModel = ViewModelProviders.of(activity!!).get(YoutubeChannelViewModel::class.java)
        searchViewModel = ViewModelProviders.of(activity!!).get(YoutubeSearchViewModel::class.java)

        searchViewModel.searchLiveData.observe(this, object: Observer<LinkedList<SearchResource>> {
            override fun onChanged(list: LinkedList<SearchResource>?) {
                list?.let{
                    searchAdapter.setItems(it)
                }
            }

        })

        searchResultsView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        searchQueryView.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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

    fun performSearch() {
        val searchQuery = searchQueryView.text.toString()
        youtubeService.findVideo(searchQuery, 10, null)
            .enqueue(object : Callback<YoutubeResponse<SearchResource>> {
                override fun onFailure(call: Call<YoutubeResponse<SearchResource>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<YoutubeResponse<SearchResource>>,
                    response: Response<YoutubeResponse<SearchResource>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!.let { response ->
                            searchViewModel.searchLiveData.value?.addAll(response.items)
                            searchViewModel.searchLiveData.notifyObserver()
                        }
                    }
                }
            })
    }

    fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}