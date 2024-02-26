package com.example.medicaltaskapplication.data


import com.example.medicaltaskapplication.data.model.Problem
import com.example.medicaltaskapplication.data.remote.MedicalApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MedicineRepositoryTest {

    @Mock
    private lateinit var mockApiService: MedicalApiService

    private lateinit var repository: MedicineRepository

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = MedicineRepository(mockApiService, testDispatcher)
    }

    @Test
    fun `getHighSchools returns expected data`() = runTest {
        val fakeHighSchools = listOf(
            Problem(id = "0", name = "Sickness 1", medications = listOf()),
            Problem(id = "0", name = "Sickness 1", medications = listOf())
        )
        `when`(mockApiService.getProblems()).thenReturn(fakeHighSchools)

        val result = repository.getProblemById("1")

        assertEquals(fakeHighSchools, result)
    }
}
