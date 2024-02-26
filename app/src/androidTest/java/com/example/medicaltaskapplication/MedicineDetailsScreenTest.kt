package com.example.medicaltaskapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.medicaltaskapplication.presentation.details.MedicineDetailsScreen
import com.example.medicaltaskapplication.presentation.details.MedicineDetailsUiState
import com.example.medicaltaskapplication.ui.theme.MedicalAppTheme
import com.example.medicaltaskapplication.utils.DummyContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description

class MedicineDetailsScreenTest {

    @get:Rule
    val testRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun loadingState_isRendered() {
        testRule.setContent {
            MedicalAppTheme {
                MedicineDetailsScreen(
                    uiState = MedicineDetailsUiState.Loading
                )
            }
        }

        testRule.onNodeWithContentDescription(
            com.example.medicaltaskapplication.utils.Description.MEDICINE_DETAILS_LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithHighSchoolDetails_isRendered() {
        val problem = DummyContent.medicine.first().problems.first()
        testRule.setContent {
            MedicalAppTheme {
                MedicineDetailsScreen(
                    uiState = MedicineDetailsUiState.Success(
                        problem
                    )
                )
            }
        }

        testRule.onNodeWithText(
            problem.id
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problem.name
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problem.medications.first().toString()
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problem.medications.last().toString()
        ).assertIsDisplayed()

        testRule.onNodeWithContentDescription(
            com.example.medicaltaskapplication.utils.Description.MEDICINE_DETAILS_LOADING
        ).assertDoesNotExist()
    }

    @Test
    fun errorState_isRendered() {
        val errorMessage = "Error Getting High School Details!"
        testRule.setContent {
            MedicalAppTheme {
                MedicineDetailsScreen(
                    uiState = MedicineDetailsUiState.Error(
                        errorMessage
                    )
                )
            }
        }

        testRule.onNodeWithText(
            errorMessage
        ).assertIsDisplayed()

        testRule.onNodeWithContentDescription(
            com.example.medicaltaskapplication.utils.Description.MEDICINE_DETAILS_LOADING
        ).assertDoesNotExist()
    }

}