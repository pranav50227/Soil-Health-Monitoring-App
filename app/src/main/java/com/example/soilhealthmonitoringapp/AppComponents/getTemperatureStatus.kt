package com.example.soilhealthmonitoringapp.AppComponents

fun getTemperatureStatus(temp: Float): String {
    return when {
        temp < 15 -> "Too Cold"
        temp > 30 -> "Too Hot"
        else -> "Optimal"
    }
}

fun getMoistureStatus(moisture: Float): String {
    return when {
        moisture < 40 -> "Too Dry"
        moisture > 80 -> "Too Wet"
        else -> "Good"
    }
}

fun getMoistureRecommendation(moisture: Float): String {
    return when {
        moisture < 40 -> "Increase irrigation or add mulch to retain moisture"
        moisture > 80 -> "Improve drainage or reduce watering frequency"
        else -> "Moisture level is appropriate for healthy plant growth"
    }
}

fun getOverallStatus(temp: Float, moisture: Float): String {
    val tempGood = temp in 15f..30f
    val moistureGood = moisture in 40f..80f

    return when {
        tempGood && moistureGood -> "Excellent"
        tempGood || moistureGood -> "Good"
        else -> "Needs Attention"
    }
}

fun getOverallRecommendation(temp: Float, moisture: Float): String {
    val tempGood = temp in 15f..30f
    val moistureGood = moisture in 40f..80f

    return when {
        tempGood && moistureGood -> "Your soil conditions are ideal for healthy plant growth."
        tempGood && !moistureGood -> "Temperature is good, but moisture levels need adjustment."
        !tempGood && moistureGood -> "Moisture is good, but temperature conditions could be improved."
        else -> "Both temperature and moisture need attention for optimal soil health."
    }
}

fun getTemperatureRecommendation(temp: Float): String {
    return when {
        temp < 15 -> "Consider warming techniques or wait for warmer weather"
        temp > 30 -> "Provide shade or increase irrigation to cool soil"
        else -> "Temperature is ideal for most crops"
    }
}
