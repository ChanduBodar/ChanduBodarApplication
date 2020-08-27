package com.chandubodar.view.ui

import androidx.lifecycle.MutableLiveData
import com.chandubodar.database.AppDatabase
import com.chandubodar.database.AppExecutors
import com.chandubodar.model.AlbumRepository
import com.chandubodar.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListViewModel : BaseViewModel() {
    val albumListLive = MutableLiveData<List<Item>>()
    val cartListLive = MutableLiveData<List<Item>>()

    fun fetchAlbumList(item: Item) {
        dataLoading.value = true
        AlbumRepository.getInstance().getAlbumList({ isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                albumListLive.value = response?.results?.let { filter(item, it) }
                if (albumListLive.value!!.size > 0) {
                    empty.value = false
                } else {
                    empty.value = true
                }
            } else {
                empty.value = true
            }
        })
    }

    fun getCartList() {
        dataLoading.value = true
        AppExecutors.getInstance().diskIO().execute(Runnable {
            val list: List<Item> = AppDatabase.getInstance().albumDAO.cartList
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    dataLoading.value = false
                    cartListLive.value = list
                    if (cartListLive.value!!.size > 0) {
                        empty.value = false
                    } else {
                        empty.value = true
                    }
                }
            }

        })
    }

    fun filter(searchItem: Item, relsults: List<Item>): List<Item>? {
        var list = ArrayList<Item>()
        for (item in relsults!!) {
            if (item.artistName?.contains(searchItem.artistName) || item.trackName?.contains(
                    searchItem.trackName
                )
            ) {
                list.add(item)
            }
        }
        return list;
    }
}