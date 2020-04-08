package com.example.omdb.di.home

import com.example.omdb.ui.details.DetailsFragment
import com.example.omdb.ui.details.FullPosterFragment
import com.example.omdb.ui.details.ratings.RatingsFragment
import com.example.omdb.ui.details.seasons.SeasonsFragment
import com.example.omdb.ui.details.teamdetails.TeamDetailsFragment
import com.example.omdb.ui.home.HomeFragment
import com.example.omdb.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeFullPosterFragment(): FullPosterFragment

    @ContributesAndroidInjector
    abstract fun contributeRatingsFragment(): RatingsFragment

    @ContributesAndroidInjector
    abstract fun contributeSeasonsFragment(): SeasonsFragment

    @ContributesAndroidInjector
    abstract fun contributeTeamDetailsFragment(): TeamDetailsFragment

}