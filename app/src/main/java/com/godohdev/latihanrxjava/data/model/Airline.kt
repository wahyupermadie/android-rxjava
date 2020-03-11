package com.godohdev.latihanrxjava.data.model

import com.google.gson.annotations.SerializedName

data class Airline(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)