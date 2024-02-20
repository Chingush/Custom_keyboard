package com.example.myapplication.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.CustomKeyboardService
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.ui.dashboard.DashboardFragment.GlobalData.global_bol
import com.example.myapplication.ui.dashboard.DashboardFragment.GlobalData.imageUri

class DashboardFragment : Fragment() {
    object GlobalData {
        var imageUri: Uri? = null
        var global_bol: Boolean? = false
    }

    private val REQUEST_SELECT_IMAGE = 1001
    private lateinit var img: ImageView
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        img = binding.imageView

        val btn = binding.button1
        var btn4 = binding.button4
        btn.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, REQUEST_SELECT_IMAGE)
        }
        val btn2 = binding.button2
        btn2.setOnClickListener {

        }
        btn4.setOnClickListener {
            global_bol = true
        }
        return root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data

            if (imageUri != null) {
                img.setImageURI(imageUri)
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}