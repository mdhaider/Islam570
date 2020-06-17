package com.nehal.seher.model.prayertimes

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "prayer_times_meta")
data class PrayerTimesMeta(
	@SerializedName("latitude")
	val latitude: Double,

	@SerializedName("longitude")
	val longitude: Double,

	@SerializedName("timezone")
	val timezone: String,

	@SerializedName("method")
	val method: Method,

	@SerializedName("latitudeAdjustmentMethod")
	val latitudeAdjustmentMethod: String,

	@SerializedName("midnightMode")
	val midnightMode: String,

	@SerializedName("school")
	val school: String,

	@SerializedName("offset")
	val offset: Offset
)