package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName

data class Offset (

	@SerializedName("Imsak") val imsak : Int,
	@SerializedName("Fajr") val fajr : Int,
	@SerializedName("Sunrise") val sunrise : Int,
	@SerializedName("Dhuhr") val dhuhr : Int,
	@SerializedName("Asr") val asr : Int,
	@SerializedName("Maghrib") val maghrib : Int,
	@SerializedName("Sunset") val sunset : Int,
	@SerializedName("Isha") val isha : Int,
	@SerializedName("Midnight") val midnight : Int
)