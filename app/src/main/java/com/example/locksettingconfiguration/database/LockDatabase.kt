package com.example.locksettingconfiguration.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.locksettingconfiguration.model.LockParams

@Database(entities = [LockParams::class], version = 10, exportSchema = false)
abstract class LockDatabase : RoomDatabase() {

    abstract fun lockDao(): LockDao
}