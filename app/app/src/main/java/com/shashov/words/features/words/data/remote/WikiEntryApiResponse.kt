package com.shashov.words.features.words.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class WikiEntryApiResponse {

    @SerializedName("batchcomplete")
    @Expose
    var batchcomplete: String? = null

    @SerializedName("query")
    @Expose
    var query: Query? = null


    inner class Query {

        @SerializedName("pages")
        @Expose
        var pages: HashMap<String, Pageval>? = null

    }


    inner class Pageval {

        @SerializedName("pageid")
        @Expose
        var pageid: Int? = null
        @SerializedName("ns")
        @Expose
        var ns: Int? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("extract")
        @Expose
        var extract: String? = null

    }

}
