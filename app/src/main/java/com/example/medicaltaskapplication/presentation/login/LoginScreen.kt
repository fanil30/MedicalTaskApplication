package com.example.medicaltaskapplication.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
fun LoginScreen(onLogin: (username: String, password: String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {  })
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    onLogin(username, password)
                } else {
                    if (username.isEmpty()) {
                        Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                    } else if (password.isEmpty()) {
                        Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun AfterLoginScreen(username: String) {
    val greeting = remember { getGreeting() }
    val showMessage = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showMessage.value) {
            Snackbar(
                /*action = {
                    TextButton(onClick = { showMessage.value = false }) {
                        Text(text = "Hide")
                    }
                }*/
            ) {
                Text(
                    text = "$greeting, $username!",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

fun getGreeting(): String {
    val currentTime = LocalTime.now()
    return when (currentTime.hour) {
        in 6..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(onLogin = { username, password ->
        println("Logged in with username: $username and password: $password")
    })
}
