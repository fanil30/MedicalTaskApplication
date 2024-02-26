package com.example.medicaltaskapplication.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicaltaskapplication.data.MedicineRepository
import com.example.medicaltaskapplication.data.model.Problem
import com.example.medicaltaskapplication.di.MainDispatcher
import com.example.medicaltaskapplication.navigation.PROBLEM_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val medicineRepository: MedicineRepository,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<MedicineDetailsUiState>(MedicineDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _uiState.value = MedicineDetailsUiState.Error(exception.message)
    }

    init {
        val problemId = savedStateHandle.get<String>(PROBLEM_ID) ?: "0"
        getMedicine(problemId)
        //to check if problemId is invalid
        /*if (problemId != "0") {
            getMedicine(problemId)
        } else {
            _uiState.value = MedicineDetailsUiState.Error("Invalid problem ID")
        }*/
    }

    fun getMedicine(problemId: String) {
        viewModelScope.launch(errorHandler + dispatcher) {
            medicineRepository.getProblemById(problemId).collect { problem ->
                _uiState.value = MedicineDetailsUiState.Success(problem)
            }
        }
    }
}

sealed interface MedicineDetailsUiState {
    data object Loading : MedicineDetailsUiState
    data class Success(val problem: Problem?) : MedicineDetailsUiState
    data class Error(val error: String?) : MedicineDetailsUiState
}