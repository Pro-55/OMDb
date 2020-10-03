package com.example.omdb.framework

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment : Fragment() {

    // Global
    private val TAG = BaseFragment::class.java.simpleName

    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backPressedCallback =
            requireActivity().onBackPressedDispatcher.addCallback(this) { onBackPressed() }
    }

    open fun onBackPressed() {
        if (!findNavController().popBackStack()) requireActivity().finish()
    }

}