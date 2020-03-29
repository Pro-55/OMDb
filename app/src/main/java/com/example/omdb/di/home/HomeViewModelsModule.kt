package com.example.omdb.di.home

import androidx.lifecycle.ViewModel
import com.example.omdb.di.ViewModelKey
import com.example.omdb.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

}