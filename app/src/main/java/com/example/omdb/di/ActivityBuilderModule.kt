package com.example.omdb.di

import com.example.omdb.di.home.*
import com.example.omdb.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @HomeScope
    @ContributesAndroidInjector(
        modules = [
            HomeViewModelsModule::class,
            HomeFragmentBuildersModule::class,
            HomeRepositoryModule::class,
            HomeApiModule::class
        ]
    )
    abstract fun contributeHomeActivity(): HomeActivity

}