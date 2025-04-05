package com.example.ywts22b1num7184

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
import com.example.ywts22b1num7184.data.SettingsManager
import com.example.ywts22b1num7184.ui.AddScreen
import com.example.ywts22b1num7184.ui.MainScreen
import com.example.ywts22b1num7184.ui.SettingsScreen
import com.example.ywts22b1num7184.ui.theme.Ywts22b1num7184Theme
import com.example.ywts22b1num7184.ui.theme.button_color

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Ywts22b1num7184Theme {
                val navController = rememberNavController()

                // SettingsManager-ийг үүсгэх
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
                                // Menu icon дээр дарахад SettingsScreen руу шилжих
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
                        startDestination = "MainScreen",  // MainScreen эхлээд харагдана
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("MainScreen") {
                            // settingsManager дамжуулах шаардлагагүй. зөвхөн navController дамжуулж байна.
                            MainScreen(navController = navController)
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
                            // settingsManager-ийг зөв дамжуулж байна
                            SettingsScreen(
                                navController = navController,
                                settingsManager = settingsManager  // settingsManager-ийг зөв дамжуулж байна
                            )
                        }
                    }
                }
            }
        }
    }
}
