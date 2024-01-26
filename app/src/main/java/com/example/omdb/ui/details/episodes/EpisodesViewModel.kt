package com.example.omdb.ui.details.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdb.domain.model.Resource
import com.example.omdb.domain.model.Season
import com.example.omdb.domain.use_case.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    // Global
    private val TAG = EpisodesViewModel::class.java.simpleName
    private val _season = MutableLiveData<Resource<Season>>()
    val season: LiveData<Resource<Season>> = _season

    fun getEpisodes(
        id: String,
        season: Int
    ) {
        getEpisodesUseCase(
            id = id,
            season = season
        )
            .onEach { _season.postValue(it) }
            .launchIn(viewModelScope)
    }
}