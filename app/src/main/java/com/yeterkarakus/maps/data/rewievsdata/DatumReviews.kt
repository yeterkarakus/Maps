package com.yeterkarakus.maps.data.rewievsdata

import com.google.gson.annotations.SerializedName

data class DatumReviews (
    @SerializedName("review_id")
    val reviewID: String,

    @SerializedName("review_text")
    val reviewText: String,

    val rating: Long,

    @SerializedName("review_datetime_utc")
    val reviewDatetimeUTC: String,

    @SerializedName("review_timestamp")
    val reviewTimestamp: Long,

    @SerializedName("review_link")
    val reviewLink: String,

    @SerializedName("review_photos")
    val reviewPhotos: List<String>? = null,

    @SerializedName("review_language")
    val reviewLanguage: String,

    @SerializedName("like_count")
    val likeCount: Long,

    @SerializedName("author_id")
    val authorID: String,

    @SerializedName("author_link")
    val authorLink: String,

    @SerializedName("author_name")
    val authorName: String,

    @SerializedName("author_photo_url")
    val authorPhotoURL: String,

    @SerializedName("author_review_count")
    val authorReviewCount: Long,

    @SerializedName("owner_response_datetime_utc")
    val ownerResponseDatetimeUTC: Any? = null,

    @SerializedName("owner_response_timestamp")
    val ownerResponseTimestamp: Any? = null,

    @SerializedName("owner_response_text")
    val ownerResponseText: Any? = null,

    @SerializedName("owner_response_language")
    val ownerResponseLanguage: Any? = null,

    @SerializedName("author_reviews_link")
    val authorReviewsLink: String,

    @SerializedName("author_local_guide_level")
    val authorLocalGuideLevel: Long? = null,

    @SerializedName("service_quality")
    val serviceQuality: Any? = null,

)