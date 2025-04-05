package com.example.ywts22b1num7184.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ywts22b1num7184.data.SettingsManager
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, settingsManager: SettingsManager) {
    val options = listOf(
        SettingsManager.SHOW_FOREIGN,
        SettingsManager.SHOW_NATIVE,
        SettingsManager.SHOW_BOTH
    )

    val scope = rememberCoroutineScope()
    val currentSetting by settingsManager.visibilityMode.collectAsState(initial = SettingsManager.SHOW_BOTH)
    var selectedOption by remember { mutableStateOf(currentSetting) }
    LaunchedEffect(currentSetting) {
        selectedOption = currentSetting
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Үзүүлэх тохиргоо", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(24.dp))

        options.forEach { text ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedOption.equals(text),
                    onClick = { selectedOption = text }
                )
                Text(text = text, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("БУЦАХ", color = Color.White)
            }

            Button(
                onClick = {
                    scope.launch {
                        settingsManager.setVisibilityMode(selectedOption)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("ХАДГАЛАХ", color = Color.White)
            }
        }
    }
}
