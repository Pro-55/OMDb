package com.example.omdb.util.extensions

import androidx.navigation.fragment.findNavController
import com.example.omdb.BaseFragment

fun BaseFragment.navigate(action: Int) {
    findNavController().navigate(action)
}