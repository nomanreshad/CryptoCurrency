package com.example.cryptocurrency.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptocurrency.presentation.coin_detail.CoinDetailScreen
import com.example.cryptocurrency.presentation.coin_list.CoinListScreen
import com.example.cryptocurrency.presentation.coin_list.CoinListViewModel
import com.example.cryptocurrency.ui.theme.CryptoCurrencyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoCurrencyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<CoinListViewModel>()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CoinList.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            route = Screen.CoinList.route
                        ) {
                            CoinListScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        composable(
                            route = Screen.CoinDetail.route + "/{coinId}"
                        ) {
                            CoinDetailScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}