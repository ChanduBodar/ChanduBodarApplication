package com.chandubodar.model.api

import com.chandubodar.model.AlbumResponse
import com.chandubodar.model.Item
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search?term=all")
    fun getAlbumList() : Call<AlbumResponse>
}