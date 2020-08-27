package com.chandubodar.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.chandubodar.R
import com.chandubodar.databinding.SearchAlbumFragementBinding
import com.chandubodar.model.Item
import java.io.Serializable

class SearchFragment : Fragment() {

    private lateinit var viewDataBinding: SearchAlbumFragementBinding
    private lateinit var item: Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = Item(1, "", "", "", "", "", "", false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewDataBinding = SearchAlbumFragementBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(viewLifecycleOwner)

        }
        viewDataBinding.itemData = item
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var buttonSearch = view.findViewById(R.id.buttonSearch) as Button
        buttonSearch.setOnClickListener {
            if (viewDataBinding.itemData!!.artistName.isEmpty() || viewDataBinding.itemData!!.trackName.isEmpty()) {
                Toast.makeText(context, R.string.str_error_msg, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val bundle = Bundle()
            bundle.putSerializable("item", viewDataBinding.itemData)
            Navigation.findNavController(it)
                .navigate(R.id.action_searchFragment_to_albumListFragment, bundle)
        }

    }

}
