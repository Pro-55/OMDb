package com.example.omdb.ui.details.teamdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.omdb.R
import com.example.omdb.databinding.FragmentTeamDetailsBinding
import com.example.omdb.framework.BaseFragment

class TeamDetailsFragment : BaseFragment() {

    //Global
    private val TAG = TeamDetailsFragment::class.java.simpleName
    private lateinit var binding: FragmentTeamDetailsBinding
    private val args by navArgs<TeamDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_team_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = args.teamDetails

        binding.imgBtnBack.setOnClickListener { onBackPressed() }

        binding.txtCast.text = data.cast

        binding.txtCrew.text = data.crew

        binding.txtDirector.text = data.director

        binding.txtProduction.text = data.production
    }

}