package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.net.Uri
import android.os.Binder
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.dashboard.DashboardFragment
import com.example.myapplication.ui.dashboard.DashboardFragment.GlobalData.global_bol
import com.example.myapplication.ui.dashboard.DashboardFragment.GlobalData.imageUri
import com.example.myapplication.ui.home.HomeFragment
import java.io.FileNotFoundException
import java.io.InputStream


class CustomKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var context: Context
    private var keyboardView: KeyboardView? = null
    private var keyboard: Keyboard? = null
    private var caps = false
    private var isRussianLayout = false
    private var receivedVar: String=""
    var lstData: ArrayList<fon_klavi_bit> = HomeFragment.GlobalVariable.lstData
    var global_bol: Boolean = DashboardFragment.GlobalData.global_bol == true
    inner class CustomKeyboardBinder : Binder() {
        fun getService(): CustomKeyboardService {
            return this@CustomKeyboardService
        }
    }
    override fun onCreate() {


        super.onCreate()
        context = applicationContext
        keyboardView = KeyboardView(this, null)
    }
    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.layout_custom_keyboard, null) as KeyboardView

        var i = 0
        while (i == 0)
        {
            if (imageUri != null )
            {
                val uri: Uri = Uri.parse(imageUri.toString()) // Преобразование URL в объект Uri
                if(global_bol == true)
                {
                    keyboard = Keyboard(this, R.xml.keyboard_layout)
                }
                else{
                    keyboard = Keyboard(this, R.xml.keyboard_layout)
                }

                try {
                    val inputStream: InputStream? =
                        contentResolver.openInputStream(uri) // Получение InputStream изображения из Uri
                    val drawable = Drawable.createFromStream(
                        inputStream,
                        uri.toString()
                    ) // Создание Drawable из InputStream
                    keyboardView!!.background =
                        drawable // Установка Drawable в качестве фонового изображения для KeyboardView

                    keyboardView!!.invalidateAllKeys()

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                i++
            }
        }




        keyboardView?.keyboard = keyboard
        keyboardView?.setOnKeyboardActionListener(this)
        return keyboardView as KeyboardView

    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }

    fun KeyboardView.findKeyByKeyCode(keyCode: Int): Keyboard.Key? {
        return keyboard?.keys?.find { it.codes[0] == keyCode }
    }

    override fun onPress(primaryCode: Int) {

    }


    override fun onRelease(primaryCode: Int) {
// Обработка отпускания клавиши
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val ic = currentInputConnection
        playClick(primaryCode)
        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> ic.deleteSurroundingText(1, 0)
            Keyboard.KEYCODE_SHIFT -> {
                caps = !caps
                keyboard!!.isShifted = caps
                keyboardView?.invalidateAllKeys()
            }
            Keyboard.KEYCODE_DONE -> ic.sendKeyEvent(
                KeyEvent(
                    KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_ENTER
                )
            )
            KeyEvent.KEYCODE_LANGUAGE_SWITCH->{
                if (isRussianLayout) {
                    keyboard = Keyboard(this, R.xml.keyboard_layout)
                    isRussianLayout = !isRussianLayout
                } else {
                    keyboard = Keyboard(this, R.xml.keys_definition_ru)
                    isRussianLayout = true
                }

                keyboardView?.keyboard = keyboard
            }
            else -> {
                var code = primaryCode.toChar()
                if (Character.isLetter(code) && caps) {
                    code = code.uppercaseChar()
                }
                ic.commitText(code.toString(), 1)
            }
        }

    }

    override fun onText(text: CharSequence?) {
// Обработка вводимого текста
    }

    override fun swipeLeft() {
        TODO("Not yet implemented")
    }

    override fun swipeRight() {
        TODO("Not yet implemented")
    }

    override fun swipeDown() {
        TODO("Not yet implemented")
    }

    override fun swipeUp() {
        TODO("Not yet implemented")
    }

    fun playClick(keyCode: Int) {
        val am = getSystemService(AUDIO_SERVICE) as AudioManager
        when (keyCode) {
            32 -> am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
            Keyboard.KEYCODE_DONE, 10 ->
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
            Keyboard.KEYCODE_DELETE -> am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE)
            else -> am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        }
    }

// Другие методы интерфейса OnKeyboardActionListener
}

private fun KeyboardView?.setBackground(imageRes: Bitmap?) {

}


