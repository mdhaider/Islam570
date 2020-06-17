package com.nehal.seher.model

import com.google.gson.annotations.SerializedName

data class SurahDel (
	@SerializedName("number") val number : Int,
	@SerializedName("name") val name : String,
	@SerializedName("englishName") val englishName : String,
	@SerializedName("englishNameTranslation") val englishNameTranslation : String,
	@SerializedName("numberOfAyahs") val numberOfAyahs : Int,
	@SerializedName("revelationType") val revelationType : String
)