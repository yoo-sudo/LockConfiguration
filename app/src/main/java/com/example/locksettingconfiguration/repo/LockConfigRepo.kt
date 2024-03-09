package com.example.locksettingconfiguration.repo

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.locksettingconfiguration.dataSource.LocalDataSource
import com.example.locksettingconfiguration.dataSource.LogTags
import com.example.locksettingconfiguration.dataSource.RemoteDataSource
import com.example.locksettingconfiguration.model.ApiState
import com.example.locksettingconfiguration.model.LockConfig
import com.example.locksettingconfiguration.model.LockConfigDetail
import com.example.locksettingconfiguration.model.LockParams
import com.example.locksettingconfiguration.model.LockRange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

class LockConfigRepo(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) {

    private val _lockConfigDetails = MutableStateFlow<List<LockConfigDetail>>(listOf())
    val lockConfigDetails : StateFlow<List<LockConfigDetail>> = _lockConfigDetails

    val response = MutableStateFlow<ApiState>(ApiState.Loading)

    // Validate the response and save the data to DB
    suspend fun fetchLockResponse() {
        remoteDataSource.getLockConfigResponse().onStart {
            Log.d(LogTags.FETCH_RESPONSE.name, "onStart")
            response.value = ApiState.Loading
        }.catch {
            Log.d(LogTags.FETCH_RESPONSE.name, "catch  ${it.message }")
            response.value = ApiState.Failure
        }.collect {
            response.value = ApiState.Success
            Log.d(LogTags.FETCH_RESPONSE.name, "collect { $it }")
            processData(it)
        }
    }

    // Process the response and save the data to DB
    private suspend fun processData(lockConfig: LockConfig) {
        val lockVoltage = LockConfigDetail(
            lockName = "Lock Voltage",
            primaryLockValue = lockConfig.lockVoltage.default,
            secondaryLockValue = lockConfig.lockVoltage.default,
            primaryLockValues = lockConfig.lockVoltage.values,
            secondaryLockValues = lockConfig.lockVoltage.values,
            lockRange = null,
            common = lockConfig.lockVoltage.common
        )


        val lockType = LockConfigDetail(
            lockName = "Lock Type",
            primaryLockValue = lockConfig.lockType.default,
            secondaryLockValue = lockConfig.lockType.default,
            primaryLockValues = lockConfig.lockType.values,
            secondaryLockValues = lockConfig.lockType.values,
            lockRange = null,
            common = lockConfig.lockType.common
        )

        val lockKick = LockConfigDetail(
            lockName = "Lock Kick",
            primaryLockValue = lockConfig.lockKick.default,
            secondaryLockValue = lockConfig.lockKick.default,
            primaryLockValues = lockConfig.lockKick.values,
            secondaryLockValues = lockConfig.lockKick.values,
            lockRange = null,
            common = lockConfig.lockKick.common
        )

        val lockRelease = LockConfigDetail(
            lockName = "Lock Release",
            primaryLockValue = lockConfig.lockRelease.default,
            secondaryLockValue = "",
            primaryLockValues = lockConfig.lockRelease.values,
            secondaryLockValues = listOf(),
            lockRange = null,
            common = lockConfig.lockRelease.common
        )

        val lockReleaseTime = LockConfigDetail(
            lockName = "Lock Release Time",
            primaryLockValue = lockConfig.lockReleaseTime.default,
            secondaryLockValue =  lockConfig.lockReleaseTime.default,
            primaryLockValues = listOf(),
            secondaryLockValues = listOf(),
            lockRange = LockRange(lockConfig.lockReleaseTime.range.min, lockConfig.lockReleaseTime.range.max) ,
            common = lockConfig.lockReleaseTime.common
        )

        val lockAngle = LockConfigDetail(
            lockName = "Lock Angle",
            primaryLockValue = lockConfig.lockAngle.default,
            secondaryLockValue = "",
            primaryLockValues = listOf(),
            secondaryLockValues = listOf(),
            lockRange = LockRange(lockConfig.lockAngle.range.min, lockConfig.lockReleaseTime.range.max) ,
            common = lockConfig.lockAngle.common
        )
        _lockConfigDetails.value = listOf(lockVoltage, lockType, lockKick, lockRelease, lockReleaseTime, lockAngle)

        if (getSavedParams().isEmpty()) {
            saveDataToDb(LockParams(0, lockVoltage.primaryLockValue, lockVoltage.secondaryLockValue, ""))
            saveDataToDb(LockParams(1, lockType.primaryLockValue, lockType.secondaryLockValue, ""))
            saveDataToDb(LockParams(2, lockKick.primaryLockValue, lockKick.secondaryLockValue, ""))
            saveDataToDb(LockParams(3, "", "", lockRelease.primaryLockValue))
            saveDataToDb(LockParams(4, "", "", lockReleaseTime.primaryLockValue))
            saveDataToDb(LockParams(5, "", "", lockAngle.primaryLockValue))
        }
    }

    // Get the saved data from DB
    fun getSavedParams() = localDataSource.getListOfParams()

    // Listen the changes from DB
    fun listenChangesFromDb() = localDataSource.listToSavedParams()

    // Save the data to DB
    private suspend fun saveDataToDb(lockParams: LockParams) {
        localDataSource.insert(lockParams)
    }

    // Update the data to DB
    suspend fun updateDataToDb(lockParams: LockParams) {
        localDataSource.update(lockParams)
    }
}