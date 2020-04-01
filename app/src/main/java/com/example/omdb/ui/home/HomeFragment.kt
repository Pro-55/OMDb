package com.example.omdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentHomeBinding
import com.example.omdb.models.Category
import com.example.omdb.models.Type
import com.example.omdb.util.transformers.ScaleTransformer

class HomeFragment : BaseFragment() {

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

    //Global
    private lateinit var binding: FragmentHomeBinding

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

    }

    private fun setupRecyclerView() {
        val adapter = CategoriesAdapter()
        adapter.listener = object : CategoriesAdapter.Listener {
            override fun onClick(category: Category, sharedView: View) {
                val action = HomeFragmentDirections.navigateHomeToSearch(category._id)
                findNavController().navigate(action)
            }
        }

        binding.recyclerCategories.adapter = adapter
        binding.recyclerCategories.setItemTransformer(ScaleTransformer())
        val categories = listOf(
            Category(
                _id = Type.MOVIES,
                icon = R.drawable.ic_movies,
                background = R.color.color_category_movie,
                title = Type.MOVIES.toString()
            ),
            Category(
                _id = Type.SERIES,
                icon = R.drawable.ic_series,
                background = R.color.color_category_series,
                title = Type.SERIES.toString()
            ),
            Category(
                _id = Type.EPISODES,
                icon = R.drawable.ic_episodes,
                background = R.color.color_category_episodes,
                title = Type.EPISODES.toString()
            )
        )
        adapter.swapData(categories)

    }

}
