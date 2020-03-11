package com.godohdev.latihanrxjava.data.model

import com.google.gson.annotations.SerializedName

data class Price(

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("flight_number")
	val flightNumber: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("to")
	val to: String? = null,

	@field:SerializedName("seats")
	val seats: Int? = null
)