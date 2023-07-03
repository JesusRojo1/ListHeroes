package com.jesus_rojo_proyects.superherolist.models

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailModel(
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerStats: PowerStats,
    @SerializedName("biography") val biography: Biography,
    @SerializedName("appearance") val appearance: AppearanceDetail,
    @SerializedName("image") val imageUrl: ImageUrlDetail,
    @SerializedName("work") val work: Work
)

data class PowerStats(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String,
)

data class Biography(
    @SerializedName("full-name") val fullName: String,
    @SerializedName("alter-egos") val alterEgos: String,
//    @SerializedName("aliases") val aliases: Aliases,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    @SerializedName("first-appearance") val firstAppearance: String,
    @SerializedName("alignment") val alignment: String,
    @SerializedName("publisher") val publisherComic: String,
)

//data class Aliases(
//    @SerializedName("gender") val gender: String,
//    @SerializedName("race") val race: String,
//)

data class AppearanceDetail(
    @SerializedName("gender") val gender: String,
    @SerializedName("race") val race: String,
)

data class Work(
    @SerializedName("occupation") val occupation: String,
    @SerializedName("base") val base: String,
)

data class ImageUrlDetail(
    @SerializedName("url") val url: String,
)