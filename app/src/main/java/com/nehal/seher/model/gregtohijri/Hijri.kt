package com.nehal.seher.model.gregtohijri

import com.google.gson.annotations.SerializedName

data class Hijri (
	@SerializedName("date") val date : String,
	@SerializedName("format") val format : String,
	@SerializedName("day") val day : Int,
	@SerializedName("weekday") val weekday : Weekday,
	@SerializedName("month") val month : Month,
	@SerializedName("year") val year : Int,
	@SerializedName("designation") val designation : Designation,
	@SerializedName("holidays") val holidays : List<String>
)