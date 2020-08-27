package com.chandubodar.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumRepository {

    // GET repo list
    fun getAlbumList(onResult: (isSuccess: Boolean, response: AlbumResponse?) -> Unit) {

        ApiClient.instance.getAlbumList().enqueue(object : Callback<AlbumResponse> {
            override fun onResponse(call: Call<AlbumResponse>?, response: Response<AlbumResponse>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<AlbumResponse>?, t: Throwable?) {
                onResult(false, null)
            }

        })
    }

    companion object {
        private var INSTANCE: AlbumRepository? = null
        fun getInstance() = INSTANCE
                ?: AlbumRepository().also {
                    INSTANCE = it
                }
    }
}