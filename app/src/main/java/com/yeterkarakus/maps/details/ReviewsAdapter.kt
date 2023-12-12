package com.yeterkarakus.maps.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.yeterkarakus.maps.data.rewievsdata.DatumReviews
import com.yeterkarakus.maps.databinding.RecyclerRowBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewsAdapter(private val reviewList: List<DatumReviews>,private val context: Context) : RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder>() {
    class ReviewsAdapterViewHolder(val binding: RecyclerRowBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapterViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewsAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewsAdapterViewHolder, position: Int) {


        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date: Date = dateFormat.parse(reviewList[position].reviewDatetimeUTC)!!

        val displayFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate: String = displayFormat.format(date)

        holder.binding.let {

            it.authorName.text = reviewList[position].authorName
            it.rating.rating = reviewList[position].rating.toFloat()
            it.dateTime.text = formattedDate
            it.reviewText.text = reviewList[position].reviewText
            Glide.with(context).load(reviewList[position].authorPhotoURL).circleCrop().into(it.authorImageView)


        }
    }

}