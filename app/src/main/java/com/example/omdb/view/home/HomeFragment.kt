package com.example.omdb.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentHomeBinding
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.extensions.getViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }

    //Global
    @Inject lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by lazy { requireActivity().getViewModel<HomeViewModel>(factory) }

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

        binding.imgLogo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigateHomeToDetails))

        viewModel.data.observe(viewLifecycleOwner, Observer { Log.d(TAG, "TestLog: sr:$it") })

    }
}
