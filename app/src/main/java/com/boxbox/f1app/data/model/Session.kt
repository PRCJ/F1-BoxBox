package com.boxbox.f1app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val sessionId: String,
    val sessionType: String,
    val sessionName: String,
    val startTime: Long,  // Changed from String to Long
    val endTime: Long,    // Changed from String to Long
    val sessionState: String
)