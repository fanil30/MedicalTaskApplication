package com.example.medicaltaskapplication.presentation.details

import androidx.lifecycle.SavedStateHandle
import com.example.medicaltaskapplication.data.MedicineRepository
import com.example.medicaltaskapplication.data.model.Problem
import com.example.medicaltaskapplication.navigation.PROBLEM_ID
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MedicineDetailsViewModelTest {

    private lateinit var medicineRepository: MedicineRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MedicineDetailsViewModel

    @Before
    fun setUp() {
        medicineRepository = mockk(relaxed = true)
    }

    @Test
    fun `Should set UI state successfully`() = runTest {
        val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
        coEvery { savedStateHandle.get<String>(PROBLEM_ID) } returns "1"
        viewModel = MedicineDetailsViewModel(savedStateHandle, medicineRepository, testDispatcher)

        val id = savedStateHandle.get<String>(PROBLEM_ID).orEmpty()
        coEvery { medicineRepository.getProblemById("1") } returns flowOf(fakeProblem)

        viewModel.uiState.value.shouldBeTypeOf<MedicineDetailsViewModel.Loading>()
        viewModel.getMedicine(id)

        val successItem = viewModel.uiState.value
        successItem.shouldBeTypeOf<MedicineDetailsUiState.Success>()
        successItem.problem.shouldBe(fakeProblem)
    }

    companion object {
        val fakeProblem = Problem(id = "0", name = "Sickness 1", medications = listOf())
    }
}