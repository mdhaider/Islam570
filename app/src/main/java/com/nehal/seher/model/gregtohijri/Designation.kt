package com.nehal.seher.model.gregtohijri

import com.google.gson.annotations.SerializedName

data class Designation (
	@SerializedName("abbreviated") val abbreviated : String,
	@SerializedName("expanded") val expanded : String
)