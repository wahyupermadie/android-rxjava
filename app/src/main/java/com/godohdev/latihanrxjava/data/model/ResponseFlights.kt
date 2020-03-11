package com.godohdev.latihanrxjava.data.model

import com.google.gson.annotations.SerializedName

data class ResponseFlights(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("instructions")
	val instructions: String? = null,

	@field:SerializedName("arrival")
	val arrival: String? = null,

	@field:SerializedName("flight_number")
	val flightNumber: String? = null,

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("to")
	val to: String? = null,

	@field:SerializedName("departure")
	val departure: String? = null,

	@field:SerializedName("stops")
	val stops: Int? = null,

	@field:SerializedName("airline")
	val airline: Airline? = null,

	@field:SerializedName("price")
	var price: Price? = null
)