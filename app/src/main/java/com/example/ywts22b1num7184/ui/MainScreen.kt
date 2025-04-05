package com.example.ywts22b1num7184.ui

import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ywts22b1num7184.data.DatabaseHelper
import com.example.ywts22b1num7184.data.SettingsManager
import com.example.ywts22b1num7184.data.SettingsManager.Companion.SHOW_FOREIGN
import com.example.ywts22b1num7184.data.SettingsManager.Companion.SHOW_NATIVE
import com.example.ywts22b1num7184.ui.theme.button_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, settingsManager: SettingsManager) {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    var words by remember { mutableStateOf(dbHelper.getAllWords()) }
    val isEmpty = words.isEmpty()

    var currentIndex by remember { mutableStateOf(0) }
    var word by remember { mutableStateOf(words.getOrNull(currentIndex)?.first ?: "") }
    var translation by remember { mutableStateOf(words.getOrNull(currentIndex)?.second ?: "") }
    var showDialog by remember { mutableStateOf(false) }
    val visibilityMode by settingsManager.visibilityMode.collectAsState(initial = SettingsManager.SHOW_BOTH)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = word,
                onValueChange = {
                    word = it
                    val index = words.indexOfFirst { pair -> pair.first.equals(it, ignoreCase = true) }
                    if (index != -1) {
                        currentIndex = index
                        translation = words[index].second
                    } else {
                        translation = "No translation"
                    }
                },
                visualTransformation = if (!visibilityMode.equals(SHOW_FOREIGN)) VisualTransformation.None else PasswordVisualTransformation(),
                label = { Text("Word") },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                navController.navigate("AddScreen/$word/$translation")
                            }
                        )
                    },
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = translation,
                onValueChange = {},
                label = { Text("Translation") },
                textStyle = MaterialTheme.typography.bodyLarge,
                visualTransformation = if (!visibilityMode.equals(SHOW_NATIVE)) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                navController.navigate("AddScreen/$word/$translation")
                            }
                        )
                    },
                enabled = false
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("AddScreen//") },
                    colors = ButtonDefaults.buttonColors(containerColor = button_color)
                ) {
                    Text("НЭМЭХ", color = Color.White)
                }
                Button(
                    onClick = { navController.navigate("AddScreen/$word/$translation") },
                    enabled = !isEmpty,
                    colors = ButtonDefaults.buttonColors(containerColor = button_color)
                ) {
                    Text("ШИНЭЧЛЭХ", color = Color.White)
                }
                Button(
                    onClick = { showDialog = true },
                    enabled = !isEmpty,
                    colors = ButtonDefaults.buttonColors(containerColor = button_color)
                ) {
                    Text("УСТГАХ", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (words.isNotEmpty() && currentIndex > 0) {
                            currentIndex -= 1
                            word = words[currentIndex].first
                            translation = words[currentIndex].second
                        }
                    },
                    enabled = !isEmpty && currentIndex > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = button_color)
                ) {
                    Text("ӨМНӨХ", color = Color.White)
                }

                Button(
                    onClick = {
                        if (words.isNotEmpty() && currentIndex < words.size - 1) {
                            currentIndex += 1
                            word = words[currentIndex].first
                            translation = words[currentIndex].second
                        }
                    },
                    enabled = !isEmpty && currentIndex < words.size - 1,
                    colors = ButtonDefaults.buttonColors(containerColor = button_color)
                ) {
                    Text("ДАРАА", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Баталгаажуулалт") },
            text = { Text("Та энэ үгийг устгахдаа итгэлтэй байна уу?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        dbHelper.deleteWord(word)
                        words = dbHelper.getAllWords()
                        currentIndex = 0
                        word = words.getOrNull(0)?.first ?: ""
                        translation = words.getOrNull(0)?.second ?: ""
                        Toast.makeText(context, "Үг устгагдлаа", Toast.LENGTH_SHORT).show()
                        showDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = button_color)
                ) {
                    Text("Тийм")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = button_color)) {
                    Text("Үгүй")
                }
            }
        )
    }
}
