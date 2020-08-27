package com.chandubodar.adapter

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chandubodar.BR
import com.chandubodar.database.AppDatabase
import com.chandubodar.database.AppExecutors
import com.chandubodar.model.Item
import kotlinx.android.synthetic.main.album_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class AlbumListViewHolder constructor(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.item_avatar
    fun setup(itemData: Item) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        Glide.with(itemView).load(itemData.artworkUrl100).into(avatarImage)
        var buttonAddCart = itemView.buttonAddToCart as Button
        buttonAddCart.setOnClickListener {
            itemData.addToCart = true
            Toast.makeText(itemView.context, "Item Added in Cart.", Toast.LENGTH_SHORT).show()
            buttonAddCart.visibility = View.GONE

            AppExecutors.getInstance().diskIO().execute(Runnable {
                AppDatabase.getInstance().albumDAO.insert(itemData)
            })
        }
        if (itemData.addToCart != null && itemData.addToCart) {
            buttonAddCart.visibility = View.GONE
        } else {
            buttonAddCart.visibility = View.VISIBLE
        }

        itemView.item_r_date.setText(getDateString(itemData.releaseDate))

    }

    fun getDateString(strDate: String): String {
        val dateMonthYearTimeFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val date: Date = dateMonthYearTimeFormat.parse(strDate)
        return outputFormat.format(date);
    }
}