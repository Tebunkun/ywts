package com.example.ywts22b1num7184.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ywts22b1num7184.data.DatabaseHelper
import com.example.ywts22b1num7184.ui.theme.button_color

@Composable
fun AddScreen(navController: NavController, word: String, translation: String ) {
    val dbHelper = DatabaseHelper(LocalContext.current)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var word by remember { mutableStateOf(word  ) }
        var translation by remember { mutableStateOf(translation) }

        TextField(
            value = word,
            onValueChange = { word = it },
            label = { Text("Word") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = translation,
            onValueChange = { translation = it },
            label = { Text("Translation") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Save & Stop buttons in a row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    dbHelper.insertWord(word, translation)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = button_color)
            ) {
                Text("ОРУУЛАХ", color = Color.White)
            }
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = button_color)
            ) {
                Text("БОЛИХ", color = Color.White)
            }
        }
    }
}
