package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName

data class Timings (

	@SerializedName("Fajr") val fajr : String,
	@SerializedName("Sunrise") val sunrise : String,
	@SerializedName("Dhuhr") val dhuhr : String,
	@SerializedName("Asr") val asr : String,
	@SerializedName("Sunset") val sunset : String,
	@SerializedName("Maghrib") val maghrib : String,
	@SerializedName("Isha") val isha : String,
	@SerializedName("Imsak") val imsak : String,
	@SerializedName("Midnight") val midnight : String
)