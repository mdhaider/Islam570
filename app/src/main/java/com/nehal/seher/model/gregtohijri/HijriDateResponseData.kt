package com.nehal.seher.model.gregtohijri

import com.google.gson.annotations.SerializedName

data class HijriDateResponseData (
	@SerializedName("hijri") val hijri : Hijri?=null,
	@SerializedName("gregorian") val gregorian : Gregorian?=null
)