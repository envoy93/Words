package com.shashov.words.features.words.data.remote

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiService {

    @GET("/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=")
    fun getWikiEntry(@Query("titles") title: String): Flowable<WikiEntryApiResponse>
}
