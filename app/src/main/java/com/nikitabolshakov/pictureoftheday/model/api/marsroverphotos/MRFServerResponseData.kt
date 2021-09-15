package com.nikitabolshakov.pictureoftheday.model.api.marsroverphotos

import com.google.gson.annotations.SerializedName

data class MRFServerResponseData(
    @SerializedName("photos") val photos: List<Photo>,
) {
    data class Photo(
        @SerializedName("camera") val camera: Camera,
        @SerializedName("earth_date") val earth_date: String,
        @SerializedName("id") val id: Int,
        @SerializedName("img_src") val img_src: String,
        @SerializedName("rover") val rover: Rover,
        @SerializedName("sol") val sol: Int
    ) {
        data class Camera(
            @SerializedName("full_name") val full_name: String,
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String,
            @SerializedName("rover_id") val rover_id: Int
        )

        data class Rover(
            @SerializedName("id") val id: Int,
            @SerializedName("landing_date") val landing_date: String,
            @SerializedName("launch_date") val launch_date: String,
            @SerializedName("name") val name: String,
            @SerializedName("status") val status: String
        )
    }
}