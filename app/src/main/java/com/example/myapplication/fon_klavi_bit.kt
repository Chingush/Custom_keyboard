package com.example.myapplication

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class fon_klavi_bit(
    var text: String? = null,
    var imageRes1: Bitmap? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeParcelable(imageRes1, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<fon_klavi_bit> {
        override fun createFromParcel(parcel: Parcel): fon_klavi_bit {
            return fon_klavi_bit(parcel)
        }

        override fun newArray(size: Int): Array<fon_klavi_bit?> {
            return arrayOfNulls(size)
        }
    }
}
