package com.example.omdb.network.responce

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SearchResult {

    @SerializedName("Search")
    @Expose
    var searchData: List<SearchData>? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = null
    @SerializedName("Response")
    @Expose
    var response: String? = null
    @SerializedName("Error")
    @Expose
    var error: String? = null
}
