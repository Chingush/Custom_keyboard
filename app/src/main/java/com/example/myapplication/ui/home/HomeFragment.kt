package com.example.myapplication.ui.home

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.CustomAdapter
import com.example.myapplication.Fon_klavi
import com.example.myapplication.MainActivity2
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.fon_klavi_bit
import com.example.myapplication.ui.home.HomeFragment.GlobalVariable.lstData
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mDataBase: DatabaseReference
    private var USER_KEY: String = "thems"
    object GlobalVariable {
        var lstData: ArrayList<fon_klavi_bit> = ArrayList()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY)
        var lstData = GlobalVariable.lstData

        getDataChange()

        return root
    }

    private fun getDataChange() {

        val listView = binding.listview
        val vListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                lstData.clear()
                for (ds: DataSnapshot in dataSnapshot.children) {
                    val fk: Fon_klavi? = ds.getValue(Fon_klavi::class.java)
                    if (fk != null) {
                        val lfk = fon_klavi_bit()


                        lfk.text = fk.text


                        val target = object : Target {
                            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                                if (bitmap != null) {
                                    lfk.imageRes1 = bitmap
                                    // Обновление адаптера здесь, после загрузки изображения
                                    val adapter = CustomAdapter(requireActivity(), lstData)
                                    listView.adapter = adapter
                                }
                            }

                                override fun onBitmapFailed(
                                    e: Exception?,
                                    errorDrawable: Drawable?
                                ) {
                                    // Обработка ошибки загрузки изображения
                                }

                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                                    // Действия при подготовке загрузки изображения
                                }
                            }

                        Picasso.get().load(fk.imageRes1).into(target)
                        lstData.add(lfk)

                    }

                }
                val adapter = CustomAdapter(requireActivity(), lstData)
                listView.adapter = adapter

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Обработка ошибки
            }
        }
        mDataBase.addValueEventListener(vListener)
        listView.setOnItemClickListener{parent, view, position, id ->
            val item = lstData[position]
            val intent = Intent(requireContext(), MainActivity2::class.java)
            intent.putExtra("extraVar", position.toString())

            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun Intent.putParcelableExtra(s: String, item: fon_klavi_bit) {

}



