package com.example.medicaltaskapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.medicaltaskapplication.data.MedicineRepository
import com.example.medicaltaskapplication.navigation.MedicalNavHost
import com.example.medicaltaskapplication.navigation.medicineDetailsPattern
import com.example.medicaltaskapplication.navigation.medicineListRoute
import com.example.medicaltaskapplication.presentation.login.AfterLoginScreen
import com.example.medicaltaskapplication.presentation.login.LoginScreen
import com.example.medicaltaskapplication.ui.component.MedicalTopAppBar

@Composable
fun MedicalTaskApp(
    appState: MedicalTaskAppState = rememberMedicalTaskAppState(),
) {
    var isLoggedIn by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }

    if (isLoggedIn) {
        Column {
            MedicalTaskContent(appState = appState, username = username)
        }
    } else {
        LoginScreen(onLogin = { userName, _ ->
            isLoggedIn = true
            username = userName
        })
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MedicalTaskContent(
    appState: MedicalTaskAppState,
    username: String? = null
) {
    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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
                appState = appState, startDestination = startDestination
            )

            if (username != null) {
                AfterLoginScreen(username = username)
                //here
            }
        }
    }
}