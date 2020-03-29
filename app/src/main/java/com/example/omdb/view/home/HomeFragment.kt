package com.example.omdb.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentHomeBinding

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

        binding.imgLogo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigateHomeToDetails))

    }
}
