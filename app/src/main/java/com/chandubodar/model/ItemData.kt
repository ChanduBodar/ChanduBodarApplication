package com.chandubodar.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Item")
data class Item(
    @PrimaryKey
    @NonNull
    var trackId: Int,
    @SerializedName("artistName")
    @Expose
    var artistName: String,
    @SerializedName("trackName")
    @Expose
    var trackName: String,
    @SerializedName("collectionName")
    @Expose
    var collectionName: String,
    @SerializedName("collectionPrice")
    @Expose
    var collectionPrice: String,
    @SerializedName("releaseDate")
    @Expose
    var releaseDate: String,
    @SerializedName("artworkUrl100")
    @Expose
    var artworkUrl100: String,
    @SerializedName("addToCart")
    @Expose
    var addToCart: Boolean
) : Serializable
