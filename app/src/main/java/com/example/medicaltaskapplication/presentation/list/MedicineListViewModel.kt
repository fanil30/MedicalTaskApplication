package com.example.medicaltaskapplication.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicaltaskapplication.data.MedicineRepository
import com.example.medicaltaskapplication.data.model.Problem
import com.example.medicaltaskapplication.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineListViewModel @Inject constructor(
    private val medicineRepository: MedicineRepository,
    @MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableStateFlow<MedicineListUiState>(MedicineListUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _uiState.value = MedicineListUiState.Error(exception.message)
    }

    init {
        getMedicines()
    }

    fun getMedicines() {
        viewModelScope.launch(errorHandler + dispatcher) {
            medicineRepository.getProblems().collect { problems ->
                _uiState.value = MedicineListUiState.Success(problems)
            }
        }
    }
}

sealed interface MedicineListUiState {
    data object Loading : MedicineListUiState
    data class Success(val problem: List<Problem>) : MedicineListUiState
    data class Error(val error: String?) : MedicineListUiState
}