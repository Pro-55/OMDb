package com.example.omdb.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.omdb.R
import com.example.omdb.databinding.FragmentMainBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    //Global
    private val TAG = MainFragment::class.java.simpleName
    @Inject lateinit var sp: SharedPreferences
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasSignedUp = sp.getBoolean(Constants.KEY_SIGN_UP_STATUS, false)

        val action = if (hasSignedUp) MainFragmentDirections.navigateMainToHome()
        else MainFragmentDirections.navigateMainToSignUp()
        findNavController().navigate(action)
    }

}