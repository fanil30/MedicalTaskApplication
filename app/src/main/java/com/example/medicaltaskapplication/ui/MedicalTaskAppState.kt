package com.example.medicaltaskapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medicaltaskapplication.navigation.medicineListRoute

@Composable
fun rememberMedicalTaskAppState(
    navController: NavHostController = rememberNavController()
): MedicalTaskAppState {
    return remember(
        navController
    ) {
        MedicalTaskAppState(
            navController
        )
    }
}

@Stable
class MedicalTaskAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val appBarTitle: String
        @Composable
        get() = when (currentDestination?.route) {
            medicineListRoute -> "Medicines List"
            else -> "Details"
        }

    fun navigateToPreviousScreen() {
        navController.popBackStack()
    }
}