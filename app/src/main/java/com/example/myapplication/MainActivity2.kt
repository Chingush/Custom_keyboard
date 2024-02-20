package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.inputmethodservice.KeyboardView
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.databinding.ActivityMain2Binding
import com.example.myapplication.ui.dashboard.DashboardFragment
import com.example.myapplication.ui.dashboard.DashboardFragment.GlobalData.imageUri
import com.example.myapplication.ui.home.HomeFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL


import kotlin.properties.Delegates

class MainActivity2 : AppCompatActivity() {

    private lateinit var keyboardView: KeyboardView
    private var receivedVar: String = ""
    private var lstData: ArrayList<fon_klavi_bit> = HomeFragment.GlobalVariable.lstData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        // Получение значения receivedVar из Intent
        receivedVar = intent.getStringExtra("extraVar").toString()

        // Установка текста и изображения на TextView и ImageView
        val btn = findViewById<Button>(R.id.button3)
        val textView = findViewById<TextView>(R.id.textView2)
        val imageView = findViewById<ImageView>(R.id.imageView2)
        textView.text = lstData[receivedVar.toInt()].text
        imageView.setImageBitmap(lstData[receivedVar.toInt()].imageRes1)

        // Установка заднего фона клавиатуры из Bitmap
        btn.setOnClickListener {
            val bitmap = lstData[receivedVar.toInt()].imageRes1
            try {
                DashboardFragment.GlobalData.imageUri = bitmap?.let { it1 -> bitmapToUri(it1) }
                // Используйте imageUri по вашему усмотрению
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }

        }
    }
    fun bitmapToUri(bitmap: Bitmap): Uri? {
        var fileOutputStream: FileOutputStream? = null

        try {
            val tempFile = File.createTempFile("temp_image", null)
            fileOutputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)

            return Uri.fromFile(tempFile)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return null
    }
}
