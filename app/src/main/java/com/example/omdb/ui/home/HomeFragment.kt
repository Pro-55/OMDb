package com.example.omdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentHomeBinding
import com.example.omdb.models.Type

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

        setupView()

    }

    private fun setupView() {

        binding.cardMovies.setOnClickListener {
            openSearch(
                Type.MOVIES,
                binding.layoutMovies,
                binding.imgIconMovies,
                binding.txtTitleMovies
            )
        }

        binding.cardSeries.setOnClickListener {
            openSearch(
                Type.SERIES,
                binding.layoutSeries,
                binding.imgIconSeries,
                binding.txtTitleSeries
            )
        }
    }

    private fun openSearch(type: Type, sharedContainer: View, sharedIcon: View, sharedTitle: View) {
        val action = HomeFragmentDirections.navigateHomeToSearch(type)
        val extras = FragmentNavigatorExtras(
            sharedContainer to sharedContainer.transitionName,
            sharedIcon to sharedIcon.transitionName,
            sharedTitle to sharedTitle.transitionName
        )
        findNavController().navigate(action, extras)
    }

}
