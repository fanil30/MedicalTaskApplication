package com.example.medicaltaskapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.medicaltaskapplication.presentation.details.MedicineDetailsRoute
import com.example.medicaltaskapplication.presentation.list.MedicineListRoute
import com.example.medicaltaskapplication.ui.MedicalTaskAppState

@Composable
fun MedicalNavHost(
    appState: MedicalTaskAppState,
    modifier: Modifier = Modifier,
    startDestination: String = medicineListRoute
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = medicineListRoute) {
            MedicineListRoute(
                onItemClick = { problem ->
                    navController.navigateToMedicineDetailsScreen(problem.id)
                }
            )
        }
        composable(
            route = medicineDetailsPattern,
            arguments = listOf(
                navArgument(PROBLEM_ID) { type = NavType.StringType }
            )
        ) {
            MedicineDetailsRoute()
        }
    }
}
