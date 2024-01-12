package com.example.notes_hoodlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes_hoodlab.detail_screen.DetailViewModel
import com.example.notes_hoodlab.home.HomeViewModel
import com.example.notes_hoodlab.login.LoginViewModel
import com.example.notes_hoodlab.nav.Navigation
import com.example.notes_hoodlab.ui.theme.Notes_HoodLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            Notes_HoodLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        loginViewModel = loginViewModel,
                        detailViewModel = detailViewModel,
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}

