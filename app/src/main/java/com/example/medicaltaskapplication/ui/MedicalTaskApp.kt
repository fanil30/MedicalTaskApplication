package com.example.medicaltaskapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.example.medicaltaskapplication.navigation.MedicalNavHost
import com.example.medicaltaskapplication.navigation.medicineDetailsPattern
import com.example.medicaltaskapplication.navigation.medicineListRoute
import com.example.medicaltaskapplication.ui.component.MedicalTopAppBar

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MedicalTaskApp(
    windowSizeClass: WindowSizeClass,
    appState: MedicalTaskAppState = rememberMedicalTaskAppState(),
) {

    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->

        Row(
            Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            Column(Modifier.fillMaxSize()) {
                when (appState.currentDestination?.route) {
                    medicineListRoute -> {
                        MedicalTopAppBar(
                            title = appState.appBarTitle
                        )
                    }
                    medicineDetailsPattern -> {
                        MedicalTopAppBar(
                            title = appState.appBarTitle,
                            navigationIcon = Icons.Rounded.ArrowBack,
                            navigationIconContentDescription = "Go Back",
                            onNavigationClick = appState::navigateToPreviousScreen
                        )
                    }
                }

                val startDestination = medicineListRoute
                MedicalNavHost(
                    appState = appState,
                    startDestination = startDestination
                )
            }
        }

    }
}