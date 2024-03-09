package com.example.locksettingconfiguration.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.locksettingconfiguration.di.LOCK_TABLE

@Entity(tableName = LOCK_TABLE)
data class LockParams(
    @PrimaryKey(autoGenerate = false)
    val index: Int = 0,
    @ColumnInfo(name = "primary_lock_value")
    val primaryLockValue: String = "",
    @ColumnInfo(name = "secondary_lock_value")
    val secondaryLockValue: String = "",
    @ColumnInfo(name = "common_value")
    val commonValue: String = "",
)