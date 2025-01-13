package com.example.cryptocurrency.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class CoinListViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    init {
        getCoins()
    }

    private fun getCoins() {
        coinUseCases.getCoins()
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
                            coins = result.data ?: emptyList(),
                            isLoading = false
                        ) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}