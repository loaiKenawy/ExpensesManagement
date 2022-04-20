package com.internshala.expenses.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExpensesEntity::class], version = 1, exportSchema = false)
abstract class ExpensesDatabase : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

    companion object {
        @Volatile
        private var instanceCheck: ExpensesDatabase? = null

        fun getExpensesDatabase(context: Context): ExpensesDatabase {
            val temp = instanceCheck
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpensesDatabase::class.java,
                    "expenses_database"
                ).build()
                instanceCheck = newInstance
                return newInstance
            }
        }
    }
}