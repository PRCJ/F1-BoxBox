package com.boxbox.f1app.data.remote

import com.boxbox.f1app.data.model.DriverResponse
import com.boxbox.f1app.data.model.RaceResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ApiService(private val client: HttpClient) {

    suspend fun getDrivers(): DriverResponse {
        val response: HttpResponse = client.get("e8616da8-220c-4aab-a670-ab2d43224ecb")
        val text = response.bodyAsText()
        println("Driver API Response: $text")
        return response.body()
    }

    suspend fun getRaces(): RaceResponse {
        val response: HttpResponse = client.get("9086a3f1-f02b-4d24-8dd3-b63582f45e67")
        val text = response.bodyAsText()
        println("Race API Response: $text")
        return response.body()
    }
}