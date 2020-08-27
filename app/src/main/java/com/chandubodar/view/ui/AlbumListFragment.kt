package com.chandubodar.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandubodar.R
import com.chandubodar.adapter.AlbumListAdapter
import com.chandubodar.databinding.FragmentAlbumListBinding
import com.chandubodar.model.Item
import com.chandubodar.view.ui.AlbumListViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*

class AlbumListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentAlbumListBinding
    private lateinit var adapter: AlbumListAdapter
    private lateinit var searchItem: Item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAlbumListBinding.inflate(inflater, container, false).apply {
            viewmodel =
                activity?.let { ViewModelProviders.of(it).get(AlbumListViewModel::class.java) }
            setLifecycleOwner(viewLifecycleOwner)

            searchItem = arguments?.getSerializable("item") as Item
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupObservers()

        var buttonMyCart = view.findViewById(R.id.buttonCart) as Button
        buttonMyCart.setOnClickListener {
            val cartList = viewDataBinding.viewmodel?.getCartList();
            val bundle = Bundle()
            /*bundle.putSerializable("cartList", cartList)*/
            Navigation.findNavController(it)
                .navigate(R.id.action_albumListFragment_to_CartFragment, bundle)
        }

        var spinner: Spinner = view.findViewById(R.id.spinner) as Spinner

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                if (adapter != null && adapter.albumList != null && adapter.albumList.size > 0) {
                    var sortedList: List<Item> = emptyList()
                    if (position == 0) {
                        sortedList = adapter.albumList.sortedByDescending({ it.releaseDate })
                        adapter?.updateRepoList(sortedList)
                    } else if (position == 1) {
                        sortedList = adapter.albumList.sortedWith(compareBy({ it.artistName }))
                        adapter?.updateRepoList(sortedList)
                    } else if (position == 2) {

                        sortedList = adapter.albumList.sortedWith(compareBy({ it.trackName }))

                    }
                    adapter?.updateRepoList(sortedList)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        viewDataBinding.viewmodel?.fetchAlbumList(searchItem)
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.albumListLive?.observe(viewLifecycleOwner, Observer {
            if (adapter != null) {
                var sortedList = it.sortedByDescending({ it.releaseDate })
                adapter?.updateRepoList(sortedList)

            }
        })

    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = AlbumListAdapter()
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager
            repo_list_rv.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )
            repo_list_rv.adapter = adapter
        }
    }
}
