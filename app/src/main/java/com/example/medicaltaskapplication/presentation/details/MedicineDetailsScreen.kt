package com.example.medicaltaskapplication.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.medicaltaskapplication.utils.Description
import com.example.medicaltaskapplication.utils.DummyContent

@Composable
fun MedicineDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: MedicineDetailsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MedicineDetailsScreen(
        modifier = modifier,
        uiState = uiState,
    )
}

@Composable
internal fun MedicineDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: MedicineDetailsUiState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .then(modifier)
    ) {
        when (uiState) {
            is MedicineDetailsUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .semantics { contentDescription = Description.MEDICINE_DETAILS_LOADING }
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            is MedicineDetailsUiState.Error -> {
                Text(
                    uiState.error ?: "Error getting medicine Details!",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }

            is MedicineDetailsUiState.Success -> {
                Column {
                    uiState.problem?.let {
                        Text(
                            it.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Medications:",
                        style = MaterialTheme.typography.labelMedium
                            .copy(color = MaterialTheme.colorScheme.primary)
                    )

                    uiState.problem?.medications?.forEach { medication ->
                        Column {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Name: ${medication.name}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Dose: ${medication.dose}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Strength: ${medication.strength}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Side Effects:",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            medication.sideEffects.forEach { sideEffect ->
                                Text(
                                    text = sideEffect,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicineDetailsScreenPreview() {
    MedicineDetailsScreen(
        uiState = MedicineDetailsUiState.Success(
            problem = DummyContent.problems.first()
        )
    )
}