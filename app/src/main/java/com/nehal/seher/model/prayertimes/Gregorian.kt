package com.nehal.seher.model.prayertimes

import com.google.gson.annotations.SerializedName


data class Gregorian (

	@SerializedName("date") val date : String,
	@SerializedName("format") val format : String,
	@SerializedName("day") val day : Int,
	@SerializedName("weekday") val weekday : Weekday,
	@SerializedName("month") val month : Month,
	@SerializedName("year") val year : Int,
	@SerializedName("designation") val designation : Designation
)