package com.example.omdb.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentHomeBinding
import com.example.omdb.models.Resource
import com.example.omdb.models.SearchResult
import com.example.omdb.models.ShortData
import com.example.omdb.models.Status
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.getViewModel
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

    //Global
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by lazy { requireActivity().getViewModel<HomeViewModel>(factory) }
    private var adapter: CategoriesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.data.observe(viewLifecycleOwner, Observer { bindData(it) })

    }

    private fun setupRecyclerView() {
        adapter = CategoriesAdapter(Glide.with(this))
        adapter?.listener = object : CategoriesAdapter.Listener {
            override fun onClick(data: ShortData) {
                Log.d(TAG, "TestLog: d:$data")
            }
        }

        binding.recyclerCategories.adapter = adapter

        binding.recyclerCategories.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1F)
                .setMinScale(0.5F)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )
    }

    private fun bindData(resource: Resource<SearchResult>) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.SUCCESS -> {
                val data = resource.data?.search ?: listOf()
                adapter?.swapData(data)
            }
            Status.ERROR -> Log.d(TAG, "TestLog: e:${resource.message}")
        }
    }


}
