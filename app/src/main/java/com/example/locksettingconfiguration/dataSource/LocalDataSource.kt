package com.example.locksettingconfiguration.dataSource

import com.example.locksettingconfiguration.database.LockDao
import com.example.locksettingconfiguration.model.LockParams

class LocalDataSource(private val lockDao: LockDao) {

    suspend fun insert(lock: LockParams) {
        lockDao.insert(lock)
    }

    suspend fun update(lock: LockParams) {
        lockDao.update(lock)
    }

    //get list of saved params from DB
    fun getListOfParams() = lockDao.getLockParams()

    //listeners for saved params
    fun listToSavedParams() = lockDao.listToSavedParams()
}