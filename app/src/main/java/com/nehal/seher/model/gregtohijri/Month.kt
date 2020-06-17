package com.nehal.seher.model.gregtohijri

import com.google.gson.annotations.SerializedName

data class Month (

	@SerializedName("number") val number : Int,
	@SerializedName("en") val en : String
)