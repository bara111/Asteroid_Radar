package com.udacity.asteroidradar.constants

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY="bTsHGGcven565zGPyl7s8Tog1vqLrwhkatNrPcQx"
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
    val todayDate = dateFormat.format(currentTime)
    val currentForDayOfWeek = calendar.time
    val seventhDayOfWeek = dateFormat.format(currentForDayOfWeek)
}