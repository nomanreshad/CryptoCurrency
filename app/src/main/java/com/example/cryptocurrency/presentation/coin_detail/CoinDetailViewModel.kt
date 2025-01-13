package com.example.cryptocurrency.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.common.Constants
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        coinUseCases.getCoin(coinId)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update { it.copy(
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false
                        ) }
                    }
                    is Resource.Loading -> {
                        _state.update { it.copy(
                            isLoading = true
                        ) }
                    }
                    is Resource.Success -> {
                        _state.update { it.copy(
                            coin = result.data,
                            isLoading = false
                        ) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}