package com.example.locksettingconfiguration.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.locksettingconfiguration.di.LOCK_TABLE
import com.example.locksettingconfiguration.model.LockParams
import kotlinx.coroutines.flow.Flow

@Dao
interface LockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lock: LockParams)

    @Update
    suspend fun update(lock: LockParams)

    @Query("select * from $LOCK_TABLE ORDER BY `index` ASC")
    fun getLockParams():MutableList<LockParams>

    @Query("select * from $LOCK_TABLE ORDER BY `index` ASC")
    fun listToSavedParams(): Flow<MutableList<LockParams>>
}