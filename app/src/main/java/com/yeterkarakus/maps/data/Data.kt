package com.yeterkarakus.maps.data

import android.os.Parcel
import android.os.Parcelable

data class Data(
    val name: String? = null,
    val businessId: String? = null,
    val phoneNumber: String? = null,
    val fullAddress: String? = null,
    val website: String? = null,
    val photoUrl: String? = null,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(businessId)
        parcel.writeString(phoneNumber)
        parcel.writeString(fullAddress)
        parcel.writeString(website)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}