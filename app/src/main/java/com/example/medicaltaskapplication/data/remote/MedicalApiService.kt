package com.example.medicaltaskapplication.data.remote

import com.example.medicaltaskapplication.data.model.Problem
import retrofit2.http.GET

interface MedicalApiService {

    @GET("b40eb846-a466-42c3-9a0f-61fd384711ca")
    suspend fun getProblems(): List<Problem>
}