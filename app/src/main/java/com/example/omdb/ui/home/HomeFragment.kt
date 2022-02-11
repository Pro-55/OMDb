package com.example.omdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.RequestOptions
import com.example.omdb.R
import com.example.omdb.databinding.FragmentHomeBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.models.DayPart
import com.example.omdb.models.Status
import com.example.omdb.models.Type
import com.example.omdb.models.User
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    //Global
    private val TAG = HomeFragment::class.java.simpleName
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val glide by lazy { glide() }
    private var greetingMessage by Delegates.observable("") { _, _, new ->
        val current = (binding.txtSwitchGreeting.currentView as? TextView)?.text
        if (current != new) binding.txtSwitchGreeting.setText(new)
    }
    private var greetingJob: Job? = null
    private var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        getUser()

        startGreetingJob()

    }

    private fun setupView() {

        binding.imgProfile.setOnClickListener { showLongSnackBar("Not there yet!") }

        binding.cardMovies.setOnClickListener {
            openSearch(
                Type.MOVIE,
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

    private fun getUser() {
        viewModel.getCurrentUser()
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> setUser(resource.data)
                    Status.ERROR -> showShortSnackBar(resource.message)
                }
            }
    }

    private fun setUser(user: User?) {
        if (user == null) return

        userName = user.firstName
        updateGreetingMessage()

        glide.load(user.profileUrl)
            .diskCacheStrategyAll()
            .circleCrop()
            .addProfilePlaceholder(requireContext())
            .apply(RequestOptions().override(200, 200))
            .into(binding.imgProfile)
    }

    private fun startGreetingJob() {
        greetingJob = lifecycleScope.launchWhenStarted {
            while (true) {
                updateGreetingMessage()
                delay(30000L)
            }
        }
    }

    private fun updateGreetingMessage() {
        val greetingBuilder = StringBuilder("Good ")
        val part: String
        val emoji: String
        when (Calendar.getInstance().getPartOfDay()) {
            DayPart.MORNING -> {
                part = "Morning"
                emoji = Constants.RISING_SUN_EMOJI
            }
            DayPart.AFTER_NOON -> {
                part = "Afternoon"
                emoji = Constants.SUN_EMOJI
            }
            DayPart.EVENING -> {
                part = "Evening"
                emoji = Constants.SETTING_SUN_EMOJI
            }
            DayPart.NIGHT -> {
                part = "Evening"
                emoji = Constants.CRESCENT_MOON_EMOJI
            }
        }
        greetingMessage = greetingBuilder.apply {
            append(part)
            if (!userName.isNullOrEmpty()) append(" $userName")
            append("! ")
            append(emoji)
        }
            .toString()
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

    override fun onDestroyView() {
        greetingJob?.cancel()
        super.onDestroyView()
    }

}