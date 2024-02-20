package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import kotlin.collections.contains as contains1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val enabledInputMethodList = imm.enabledInputMethodList

        val packageName = "com.example.myapplication"
        val keyboardServiceName = "$packageName/.CustomKeyboardService"

        var isKeyboardEnabled = false

        for (inputMethod in enabledInputMethodList) {
            if (inputMethod.id == keyboardServiceName) {
                isKeyboardEnabled = true

//                // Дополнительно проверяем, является ли она текущей клавиатурой
//                val currentKeyboardId = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
//                if (currentKeyboardId == keyboardServiceName) {
//
//                } else {
//
//                }

                break
            }
        }



        if (isKeyboardEnabled) {
            button.setBackgroundColor(Color.GRAY)
            button.isClickable = false

            startActivity(Intent(this, MainActivity3::class.java))
            finish()

        }
        else{
            button.setOnClickListener {
                val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
                startActivity(intent)
            }
        }

    }
}