package com.example.ywts22b1num7184

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.ywts22b1num7184.data.DatabaseHelper

class AddWordActivity : AppCompatActivity() {
//    private lateinit var dbHelper: DatabaseHelper
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_add_word)
//
//        dbHelper = DatabaseHelper(this)
//        val receivedForeignText = intent.getStringExtra("foreignWord")
//        val receivedTranslationText = intent.getStringExtra("translationWord")
////        val wordEditText: EditText = findViewById(R.id.wordEditText)
////        val translationEditText: EditText = findViewById(R.id.translationEditText)
////        val saveButton: Button = findViewById(R.id.saveButton)
//        wordEditText.setText(receivedForeignText);
//        translationEditText.setText(receivedTranslationText);
//        saveButton.setOnClickListener {
//            val word = wordEditText.text.toString()
//            val translation = translationEditText.text.toString()
//
//            if (word.isNotEmpty() && translation.isNotEmpty()) {
//                val isInserted = dbHelper.insertWord(word, translation)
//                if (isInserted) {
//                    Toast.makeText(this, "Үг амжилттай нэмэгдлээ", Toast.LENGTH_SHORT).show()
//
//
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    finish() // AddWordActivity-ийг хаах
//                } else {
//                    Toast.makeText(this, "Өгөгдлийн сан руу хадгалах үед алдаа гарлаа", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Бүх талбарыг бөглөнө үү", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
