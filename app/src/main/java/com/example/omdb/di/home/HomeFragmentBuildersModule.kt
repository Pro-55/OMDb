package com.example.omdb.di.home

import com.example.omdb.view.details.DetailsFragment
import com.example.omdb.view.home.HomeFragment
import com.example.omdb.view.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment

}