package com.example.medicaltaskapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.medicaltaskapplication.presentation.details.MedicineDetailsScreen
import com.example.medicaltaskapplication.presentation.details.MedicineDetailsUiState
import com.example.medicaltaskapplication.ui.theme.MedicalAppTheme
import com.example.medicaltaskapplication.utils.Description
import com.example.medicaltaskapplication.utils.DummyContent
import org.junit.Rule
import org.junit.Test

class MedicineListScreenTest {

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
            Description.MEDICINE_LIST_LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun stateWithHighSchools_isRendered() {
        val problemList = DummyContent.problems
        testRule.setContent {
            MedicalAppTheme {
                MedicineDetailsScreen(
                    uiState = MedicineDetailsUiState.Success(
                        problemList.first()
                    )
                )
            }
        }

        testRule.onNodeWithText(
            problemList[0].id
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problemList[0].name
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problemList[0].medications.first().name
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problemList[0].medications.first().dose
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problemList[0].medications.first().strength
        ).assertIsDisplayed()

        testRule.onNodeWithText(
            problemList[0].medications.first().sideEffects.first()
        ).assertIsDisplayed()

        testRule.onNodeWithContentDescription(
            Description.MEDICINE_LIST_LOADING
        ).assertDoesNotExist()
    }

    @Test
    fun errorState_isRendered() {
        val errorMessage = "Error Getting High Schools!"
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
            Description.MEDICINE_LIST_LOADING
        ).assertDoesNotExist()
    }


    @Test
    fun stateWithHighSchools_ClickOnItem_isRegistered() {
        val problem = DummyContent.problems.first()

        testRule.setContent {
            MedicalAppTheme {
                MedicineDetailsScreen(
                    uiState = MedicineDetailsUiState.Success(problem),
                )
            }
        }

        testRule.onNodeWithText(problem.id)
            .performClick()
    }

}