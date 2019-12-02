package com.example.youtubemanager

import android.content.SearchRecentSuggestionsProvider
import android.provider.SearchRecentSuggestions

class SearchSuggestionProvider: SearchRecentSuggestionsProvider(){
    companion object{
        const val AUTHORITY = "com.example.youtubemanager.SearchSuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(AUTHORITY, MODE)
    }
}