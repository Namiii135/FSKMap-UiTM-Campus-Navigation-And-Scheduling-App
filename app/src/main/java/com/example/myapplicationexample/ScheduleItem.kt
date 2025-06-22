package com.example.myapplicationexample

data class ScheduleItem(
    val scheduleId: Int,
    val roomName: String,
    val className: String,
    val lecturerName: String,
    val date: String,
    val startTime: String,
    val endTime: String
)
