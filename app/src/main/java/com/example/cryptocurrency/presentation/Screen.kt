package com.example.cryptocurrency.presentation

sealed class Screen(val route: String) {
    data object CoinList: Screen("coin_list_screen")
    data object CoinDetail: Screen("coin_detail_screen")
}