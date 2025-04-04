package com.example.ywts22b1num7184.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ywts22b1num7184.WordApp
import com.example.ywts22b1num7184.data.DatabaseHelper

@Composable
fun MainScreen(navController: NavController, viewModel: WordApp) {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    var words by remember { mutableStateOf(dbHelper.getAllWords()) }
    var currentIndex by remember { mutableStateOf(0) }
    val isEmpty = words.isEmpty()

    val word = words.getOrNull(currentIndex)?.first ?: "No word"
    val translation = words.getOrNull(currentIndex)?.second ?: "No translation"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Word display
        OutlinedTextField(
            value = word,
            onValueChange = {
                val index = words.indexOfFirst { pair -> pair.first.equals(it, ignoreCase = true) }
                if (index != -1) {
                    currentIndex = index
                }
            },
            label = { Text("Word") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Translation display
        TextField(
            value = translation, // ✅ Corrected value to show translation
            onValueChange = {},
            label = { Text("Translation") },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            enabled = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons for adding, editing, deleting words
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate("AddScreen/{word}/translation")
                }) {
                Text("Add Word")
            }
            Button(
                onClick = { navController.navigate("AddScreen/{word}/translation") },
                enabled = !isEmpty
            ) {
                Text("Edit Word")
            }
            Button(
                onClick = {
                    dbHelper.deleteWord(word)
                    words = dbHelper.getAllWords()
                    currentIndex = 0
                    Toast.makeText(context, "Word deleted", Toast.LENGTH_SHORT).show()
                },
                enabled = !isEmpty
            ) {
                Text("Delete")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Navigation buttons (Prev / Next)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (words.isNotEmpty()) {
                        currentIndex = (currentIndex - 1 + words.size) % words.size
                    }
                },
                enabled = !isEmpty
            ) {
                Text("Prev Word")
            }
            Button(
                onClick = {
                    if (words.isNotEmpty()) {
                        currentIndex = (currentIndex + 1) % words.size
                    }
                },
                enabled = !isEmpty
            ) {
                Text("Next Word")
            }
        }
    }
}
