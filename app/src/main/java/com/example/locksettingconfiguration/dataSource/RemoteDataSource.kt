package com.example.locksettingconfiguration.dataSource

import com.example.locksettingconfiguration.APIClient
import com.example.locksettingconfiguration.model.LockConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource {
    private var apiClient: ILockApi? = null

    init {
        apiClient = APIClient.getApiClient().create(ILockApi::class.java)
    }

    // Fetch lock configuration from server
    fun getLockConfigResponse(): Flow<LockConfig> = flow {
        emit(apiClient!!.getLockResponse())
    }.flowOn(Dispatchers.IO)
}