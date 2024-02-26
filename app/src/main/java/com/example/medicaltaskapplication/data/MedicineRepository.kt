package com.example.medicaltaskapplication.data

import com.example.medicaltaskapplication.data.model.Problem
import com.example.medicaltaskapplication.data.remote.MedicalApiService
import com.example.medicaltaskapplication.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicineRepository @Inject constructor(
    private val restInterface: MedicalApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getProblems(): Flow<List<Problem>> = flow {
        val response = withContext(dispatcher) {
            restInterface.getProblems()
        }
        emit(response)
    }.flowOn(dispatcher)

    suspend fun getProblemById(problemId: String): Flow<Problem?> = flow {
        val problems = restInterface.getProblems()
        val problem = problems.firstOrNull { it.id == problemId }
        emit(problem)
    }.flowOn(dispatcher)

}
