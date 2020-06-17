package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName


data class Date (

	@SerializedName("readable") val readable : String,
	@SerializedName("timestamp") val timestamp : Int,
	@SerializedName("gregorian") val gregorian : Gregorian,
	@SerializedName("hijri") val hijri : Hijri
)