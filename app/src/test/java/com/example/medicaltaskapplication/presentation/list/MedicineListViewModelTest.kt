package com.example.medicaltaskapplication.presentation.list

import com.example.medicaltaskapplication.data.MedicineRepository
import com.example.medicaltaskapplication.data.model.Problem
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

class MedicineListViewModelTest {

    private lateinit var medicineRepository: MedicineRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MedicineListViewModel

    @Before
    fun setUp() {
        medicineRepository = mockk(relaxed = true)
        viewModel = MedicineListViewModel(medicineRepository, testDispatcher)
    }

    @Test
    fun `Should set UI state successfully`() = runTest {
        coEvery { medicineRepository.getProblems() } returns flowOf(fakeMedicine)

        viewModel.uiState.value.shouldBeTypeOf<MedicineListUiState.Loading>()
        viewModel.getMedicines()

        val successItem = viewModel.uiState.value
        successItem.shouldBeTypeOf<MedicineListUiState.Success>()
        successItem.medicine.shouldBe(fakeMedicine)
    }

    @Test
    fun `Should set UI state to error when exception occurs`() = runTest {
        val exception = Exception("Error getting high schools")
        coEvery { medicineRepository.getProblems() } throws exception

        viewModel.uiState.value.shouldBeTypeOf<MedicineListUiState.Loading>()
        viewModel.getMedicines()

        val errorItem = viewModel.uiState.value
        errorItem.shouldBeTypeOf<MedicineListUiState.Error>()
        errorItem.error.shouldBe(exception.message)
    }

    companion object {
        val fakeMedicine = listOf(
            Problem(id = "0", name = "Sickness 1", medications = listOf()),
            Problem(id = "1", name = "Sickness 2", medications = listOf())

        )
    }
}