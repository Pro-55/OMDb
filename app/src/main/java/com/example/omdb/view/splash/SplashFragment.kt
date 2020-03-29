package com.example.omdb.view.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSplashBinding
import com.example.omdb.util.extensions.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SplashFragment : BaseFragment() {

    companion object {
        private val TAG = SplashFragment::class.java.simpleName
    }

    //Global
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                delay(2000)
                navigate(R.id.navigateSplashToHome)
            }
        }
    }
}
