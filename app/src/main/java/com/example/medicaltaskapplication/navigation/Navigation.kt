package com.example.medicaltaskapplication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

const val medicineListRoute = "medicine_list_route"
const val medicineDetailsRoute = "medicine_details_route"
const val PROBLEM_ID = "problem_id"
const val medicineDetailsPattern = "$medicineDetailsRoute/{$PROBLEM_ID}"

fun NavController.navigateToMedicineDetailsScreen(
    id: String,
    navOptions: NavOptions? = null
) {
    this.navigate("$medicineDetailsRoute/$id", navOptions)
}