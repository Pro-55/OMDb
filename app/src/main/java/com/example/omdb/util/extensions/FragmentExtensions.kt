package com.example.omdb.util.extensions

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.omdb.framework.BaseFragment

fun BaseFragment.navigate(action: Int, bundle: Bundle? = null) {
    findNavController().navigate(action, bundle)
}