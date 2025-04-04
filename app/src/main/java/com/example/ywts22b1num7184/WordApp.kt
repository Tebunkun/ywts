package com.example.ywts22b1num7184

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ywts22b1num7184.data.DatabaseHelper

class WordApp() : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var words: List<Pair<String, String>>
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        words = dbHelper.getAllWords()

        // Bind UI components to variables
//        val wordEditText: EditText = findViewById(R.id.wordTextView)
//        val translationTextView: TextView = findViewById(R.id.translationTextView)
//        val nextButton: Button = findViewById(R.id.nextButton)
//        val prevButton: Button = findViewById(R.id.prevButton)
//        val addButton: Button = findViewById(R.id.addButton)
//        val editButton: Button = findViewById(R.id.editButton)
//        val deleteButton: Button = findViewById(R.id.deleteButton)

        // Update display initially
//        updateWordDisplay(wordEditText, translationTextView)
//
//        // TextWatcher to search translation when typing
//        wordEditText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                // Update translation when word is found
//                val foundTranslation = dbHelper.findWord(s.toString())
//                if (foundTranslation != null) {
//                    translationTextView.setText(foundTranslation)
//                }
//            }
//        })
//
//        // Long click on word or translation to edit
//        val onLongClickListener = { _: Any ->
//            val intent = Intent(this, AddWordActivity::class.java).apply {
//                putExtra("foreignWord", wordEditText.text.toString())
//                putExtra("translationWord", translationTextView.text.toString())
//            }
//            startActivity(intent)
//            true
//        }
//
//        wordEditText.setOnLongClickListener(onLongClickListener)
//        translationTextView.setOnLongClickListener(onLongClickListener)
//
//        // Next button logic
//        nextButton.setOnClickListener {
//            if (words.isNotEmpty()) {
//                currentIndex = (currentIndex + 1) % words.size
//                updateWordDisplay(wordEditText, translationTextView)
//            }
//        }
//
//        // Previous button logic
//        prevButton.setOnClickListener {
//            if (words.isNotEmpty()) {
//                currentIndex = (currentIndex - 1 + words.size) % words.size
//                updateWordDisplay(wordEditText, translationTextView)
//            }
//        }
//
//        // Delete button logic
//        deleteButton.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Delete word")
//                .setMessage("Are you sure you want to delete?")
//                .setPositiveButton("Yes") { _, _ ->
//                    dbHelper.deleteWord(wordEditText.text.toString())
//                    words = dbHelper.getAllWords() // Update words after deletion
//                    currentIndex = 0
//                    updateWordDisplay(wordEditText, translationTextView) // Update display
//                    Toast.makeText(this, "Word deleted", Toast.LENGTH_SHORT).show()
//                }
//                .setNegativeButton("No", null)
//            builder.create().show()
//        }
//
//        // Add word button logic
//        addButton.setOnClickListener {
//            startActivity(Intent(this, AddWordActivity::class.java)) // Navigate to AddWordActivity
//        }
//
//        // Edit word button logic
//        editButton.setOnClickListener {
//            val intent = Intent(this, AddWordActivity::class.java).apply {
//                putExtra("foreignWord", wordEditText.text.toString())
//                putExtra("translationWord", translationTextView.text.toString())
//            }
//            startActivity(intent) // Navigate to AddWordActivity for editing
//        }
    }

    // Update UI with current word and translation
    private fun updateWordDisplay(wordTextView: TextView, translationTextView: TextView) {
        if (words.isNotEmpty()) {
            val (foreignWord, nativeWord) = words[currentIndex]
            wordTextView.text = foreignWord
            translationTextView.text = nativeWord
        } else {
            wordTextView.text = "No words available"
            translationTextView.text = ""
        }
    }

    // Ensure the UI is updated when returning from the Add/Edit screens
    override fun onResume() {
        super.onResume()
        words = dbHelper.getAllWords() // Refresh the list of words
//        updateWordDisplay(findViewById(R.id.wordTextView), findViewById(R.id.translationTextView)) // Update display
    }
}
