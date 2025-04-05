package com.example.ywts22b1num7184.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ywts22b1num7184.data.SettingsManager
import com.example.ywts22b1num7184.data.SettingsPreference
import com.example.ywts22b1num7184.data.WordDatabase
import kotlinx.coroutines.flow.first

@Composable
fun WordScreen(navController: NavController) {
    val context = LocalContext.current
    val db = WordDatabase.getDatabase(context)
    val wordDao = db.wordDao()
    val words by wordDao.getAllWords().collectAsState(initial = emptyList())

    // Create an instance of SettingsManager
    val settingsStore = remember { SettingsManager(context) }

    // Collect the visibility mode from DataStore
    val displayOption by settingsStore.visibilityMode.collectAsState(initial = SettingsManager.SHOW_BOTH)

    var currentIndex by remember { mutableStateOf(0) }
    var showNativeWord by remember { mutableStateOf(false) }

    if (words.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Үг олдсонгүй.")
        }
        return
    }

    val word = words[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (displayOption == SettingsManager.SHOW_BOTH) {
            Text("Гадаад: ${word.foreignWord}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Монгол: ${word.nativeWord}")
        } else if (displayOption == SettingsManager.SHOW_FOREIGN) {
            Text("Гадаад: ${word.foreignWord}")
        } else if (displayOption == SettingsManager.SHOW_NATIVE) {
            Text(
                text = if (showNativeWord) "Монгол: ${word.nativeWord}" else "Монгол: ****",
                modifier = Modifier
                    .clickable { showNativeWord = true }
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            showNativeWord = false
            currentIndex = (currentIndex + 1) % words.size
        }) {
            Text("Дараах үг")
        }
    }
}
