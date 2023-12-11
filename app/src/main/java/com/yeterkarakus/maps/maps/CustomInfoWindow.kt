package com.yeterkarakus.maps.maps

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.yeterkarakus.maps.R

class CustomInfoWindow(context : Context) : InfoWindowAdapter {
    private var mWindow: View = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null)

    private fun setInfoWindowText(marker: Marker) {
        val mtTitle = marker.title
        val mRating = marker.snippet
        val title = mWindow.findViewById<TextView>(R.id.title)
        val rating = mWindow.findViewById<TextView>(R.id.rating)
        if (!TextUtils.isEmpty(mtTitle)) {
            title.text = mtTitle
            rating.text = mRating

        }
    }
    override fun getInfoContents(p0: Marker): View {
        setInfoWindowText(p0)
        return mWindow
    }

    override fun getInfoWindow(p0: Marker): View {
        setInfoWindowText(p0)
        return mWindow
    }
}