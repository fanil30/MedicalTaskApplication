package com.example.medicaltaskapplication.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.medicaltaskapplication.data.model.Problem
import com.example.medicaltaskapplication.ui.component.MedicineItem
import com.example.medicaltaskapplication.utils.Description
import com.example.medicaltaskapplication.utils.DummyContent

@Composable
fun MedicineListRoute(
    modifier: Modifier = Modifier,
    onItemClick: (Problem) -> Unit,
    viewModel: MedicineListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MedicineListScreen(
        modifier = modifier,
        uiState = uiState,
        onItemClick = onItemClick
    )
}

@Composable
internal fun MedicineListScreen(
    modifier: Modifier = Modifier,
    uiState: MedicineListUiState,
    onItemClick: (Problem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .then(modifier)
    ) {
        when (uiState) {
            is MedicineListUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .semantics { contentDescription = Description.MEDICINE_LIST_LOADING }
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            is MedicineListUiState.Error -> {
                Text(
                    uiState.error ?: "Error getting Medicines!",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }

            is MedicineListUiState.Success -> {
                Column {
                    Text(
                        text = "Problems",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(uiState.problem) { prob ->
                            MedicineItem(
                                title = prob.name,
                                modifier = Modifier.clickable {
                                    onItemClick(prob)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicineListScreenPreview() {
    MedicineListScreen(
        uiState = MedicineListUiState.Success(
            problem = DummyContent.medicine
        ),
        onItemClick = { _ -> }
    )
}
