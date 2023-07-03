package com.jesus_rojo_proyects.superherolist.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class SuperHeroModel(
    @SerializedName("response") val response: String,
    @SerializedName("results-for") val results_for: String,
    @SerializedName("results") val results: List<Results>,
)

data class Results(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerStats: PowerStats,
    @SerializedName("appearance") val appearance: Appearance,
    @SerializedName("image") val imageUrl: ImageUrl,
)

data class Appearance(
    @SerializedName("gender") val gender: String,
    @SerializedName("race") val race: String,
)

data class ImageUrl(
    @SerializedName("url") val url: String,
)