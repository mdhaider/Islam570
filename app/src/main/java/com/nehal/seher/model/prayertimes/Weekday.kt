package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName

data class Weekday (
	@SerializedName("en") val en : String,
	@SerializedName("ar") val ar : String
)