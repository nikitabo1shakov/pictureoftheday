package com.nikitabolshakov.pictureoftheday.presentation.model.earth

import com.google.gson.annotations.SerializedName

data class EarthServerResponseData(
    @SerializedName("date") val date: String,
    @SerializedName("id") val id: String,
    @SerializedName("resource") val resource: Resource,
    @SerializedName("service_version") val serviceVersion: String,
    @SerializedName("url") val url: String
) {
    data class Resource(
        @SerializedName("dataset") val dataset: String,
        @SerializedName("planet") val planet: String
    )
}