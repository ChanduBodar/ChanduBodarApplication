package com.chandubodar.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandubodar.adapter.AlbumListAdapter
import com.chandubodar.databinding.FragmentAlbumListBinding
import com.chandubodar.databinding.FragmentCartListBinding
import com.chandubodar.model.Item
import com.chandubodar.view.ui.AlbumListViewModel
import kotlinx.android.synthetic.main.fragment_album_list.*

class CartFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentCartListBinding
    private lateinit var adapter: AlbumListAdapter
    private lateinit var list: ArrayList<Item>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCartListBinding.inflate(inflater, container, false).apply {
            viewmodel =
                activity?.let { ViewModelProviders.of(it).get(AlbumListViewModel::class.java) }
            setLifecycleOwner(viewLifecycleOwner)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.cartListLive?.observe(viewLifecycleOwner, Observer {
            if (adapter != null) {
                adapter?.updateRepoList(it)
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
