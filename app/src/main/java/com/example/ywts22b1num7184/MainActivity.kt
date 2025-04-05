package com.example.ywts22b1num7184

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.ywts22b1num7184.data.SettingsManager
import com.example.ywts22b1num7184.notification.ReminderWorker
import com.example.ywts22b1num7184.ui.AddScreen
import com.example.ywts22b1num7184.ui.MainScreen
import com.example.ywts22b1num7184.ui.SettingsScreen
import com.example.ywts22b1num7184.ui.theme.Ywts22b1num7184Theme
import com.example.ywts22b1num7184.ui.theme.button_color
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Ywts22b1num7184Theme {
                val navController = rememberNavController()

                val settingsManager = SettingsManager(applicationContext)

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Картны апп")
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = button_color,
                                titleContentColor = Color.White
                            ),
                            actions = {
                                IconButton(onClick = {
                                    navController.navigate("SettingsScreen")
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.menu),
                                        contentDescription = "Menu"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "MainScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("MainScreen") {
                            MainScreen(
                                navController = navController,
                                settingsManager = settingsManager
                            )
                        }

                        composable("AddScreen/{word}/{translation}") { backStackEntry ->
                            val word = backStackEntry.arguments?.getString("word") ?: ""
                            val translation = backStackEntry.arguments?.getString("translation") ?: ""
                            AddScreen(
                                navController = navController,
                                word = word,
                                translation = translation
                            )
                        }

                        composable("SettingsScreen") {
                            SettingsScreen(
                                navController = navController,
                                settingsManager = settingsManager
                            )
                        }
                    }
                }
            }
        }

        fun setupNotificationWorker(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(24, TimeUnit.HOURS)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "word_notification_work",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }

    }
}

