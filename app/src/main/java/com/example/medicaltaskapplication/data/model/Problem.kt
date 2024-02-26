package com.example.medicaltaskapplication.data.model

data class Problem(
    val id: String,
    val name: String,
    val medications: List<Medication>
)

data class Medication(
    val name: String,
    val dose: String,
    val strength: String,
    val sideEffects: List<String>
)