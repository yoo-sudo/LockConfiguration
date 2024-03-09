package com.example.locksettingconfiguration.di

import android.content.Context
import androidx.room.Room
import com.example.locksettingconfiguration.database.LockDatabase

const val DATABASE_NAME = "lock_database"
const val LOCK_TABLE = "lock_table"

fun provideDataBase(context: Context) = Room.databaseBuilder(context, LockDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build()

fun provideDao(db: LockDatabase) = db.lockDao()

