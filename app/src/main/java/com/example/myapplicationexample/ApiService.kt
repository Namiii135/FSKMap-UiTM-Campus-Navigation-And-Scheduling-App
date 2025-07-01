package com.example.myapplicationexample

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("add_schedule.php")
    fun addSchedule(
        @Field("lecturerId") lecturerId: Int,
        @Field("roomId") roomId: Int,
        @Field("classId") classId: Int,
        @Field("date") date: String,
        @Field("startTime") startTime: String,
        @Field("endTime") endTime: String
    ): Call<BasicResponse>

    @GET("get_schedule.php")
    fun getSchedule(): Call<List<ScheduleItem>>
}
