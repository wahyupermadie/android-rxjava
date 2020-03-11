package com.godohdev.latihanrxjava.data.network

import com.godohdev.latihanrxjava.data.model.Price
import com.godohdev.latihanrxjava.data.model.ResponseFlights
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Created by Wahyu Permadi on 11/03/20.
 * Android Engineer
 *
 **/

interface ApiService {
    @GET("airline-tickets.php")
    fun searchTickets(
        @Query("from") from:String,
        @Query("to") to:String) : Single<List<ResponseFlights>>

    @GET("airline-tickets-price.php")
    fun getPrice(
        @Query("flight_number") flightNumber:String,
        @Query("from") from:String,
        @Query("to") to:String) : Single<Price>
}