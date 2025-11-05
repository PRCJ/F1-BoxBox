package com.boxbox.f1app.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {

    // All methods now accept Long instead of String
    private fun timestampToInstant(timestamp: Long): Instant {
        return Instant.ofEpochSecond(timestamp)
    }

    fun formatToLocalTime(timestamp: Long): String {
        return try {
            val instant = timestampToInstant(timestamp)
            val localDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            localDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"))
        } catch (e: Exception) {
            timestamp.toString()
        }
    }

    fun formatToTime(timestamp: Long): String {
        return try {
            val instant = timestampToInstant(timestamp)
            val localDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            timestamp.toString()
        }
    }

    fun formatToDate(timestamp: Long): String {
        return try {
            val instant = timestampToInstant(timestamp)
            val localDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            localDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        } catch (e: Exception) {
            timestamp.toString()
        }
    }

    fun isUpcoming(startTime: Long, endTime: Long): Boolean {
        return try {
            val now = Instant.now()
            val end = timestampToInstant(endTime)
            now.isBefore(end)
        } catch (e: Exception) {
            false
        }
    }

    fun getNextSession(sessions: List<com.boxbox.f1app.data.model.Session>): com.boxbox.f1app.data.model.Session? {
        val now = Instant.now()
        return sessions
            .filter { session ->
                try {
                    val startTime = timestampToInstant(session.startTime)
                    startTime.isAfter(now)
                } catch (e: Exception) {
                    false
                }
            }
            .minByOrNull { session ->
                try {
                    timestampToInstant(session.startTime)
                } catch (e: Exception) {
                    Instant.MAX
                }
            }
    }
}