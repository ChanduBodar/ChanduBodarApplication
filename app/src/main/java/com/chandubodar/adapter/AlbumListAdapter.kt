package com.chandubodar.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chandubodar.databinding.AlbumItemBinding
import com.chandubodar.model.Item

import android.widget.Filter
import android.widget.Filterable
import java.util.*
import kotlin.collections.ArrayList

class AlbumListAdapter() :
    RecyclerView.Adapter<AlbumListViewHolder>(), Filterable {
    var albumList: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumListViewHolder(dataBinding)
    }

    override fun getItemCount() = albumList.size

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.setup(albumList[position])
    }

    fun updateRepoList(repoList: List<Item>) {
        this.albumList = repoList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    albumList = albumList
                } else {
                    val resultList = ArrayList<Item>()
                    for (row in albumList) {
                        if (row.artistName.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.trackName.toLowerCase(
                                Locale.ROOT
                            )
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.collectionName.toLowerCase(
                                Locale.ROOT
                            )
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.collectionPrice.toLowerCase(
                                Locale.ROOT
                            ).contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    albumList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = albumList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if(results?.values != null) {
                    albumList = results?.values as ArrayList<Item>
                } else {
                    albumList = emptyList()

                }
                notifyDataSetChanged()
            }

        }
    }

}