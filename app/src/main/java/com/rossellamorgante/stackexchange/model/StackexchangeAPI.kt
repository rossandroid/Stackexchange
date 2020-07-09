package com.rossellamorgante.stackexchange.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface StackexchangeAPI {

    @GET("/2.2/users")
    fun getUsers(@Query("order") order: String,
                 @Query("sort") sort: String,
                 @Query("inname") inname: String,
                 @Query("site") site: String):
            Single<Items>



}