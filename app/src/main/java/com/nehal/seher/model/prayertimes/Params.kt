package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName

data class Params (

	@SerializedName("Fajr") val fajr : Int,
	@SerializedName("Isha") val isha : Int
)