package com.example.omdb

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {

    companion object {
        private val TAG = BaseFragment::class.java.simpleName
    }

    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backPressedCallback =
            requireActivity().onBackPressedDispatcher.addCallback(this) { onBackPressed() }
    }

    open fun onBackPressed() {
        parentFragmentManager.popBackStack()
    }

}