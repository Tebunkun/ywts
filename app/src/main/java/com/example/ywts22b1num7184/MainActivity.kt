package com.example.ywts22b1num7184

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.ywts22b1num7184.ui.MainScreen
import com.example.ywts22b1num7184.ui.theme.Ywts22b1num7184Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Ywts22b1num7184Theme {
                val navController = rememberNavController()
                val viewModel = remember { WordApp() }

                MainScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}

