package com.internshala.expenses.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpensesDao {

    @Query("SELECT * FROM ExpensesEntity")
    fun getAllExpenses(): LiveData<List<ExpensesEntity>>

    @Query("SELECT pendingBalance FROM ExpensesEntity ORDER BY ID DESC LIMIT 1")
    fun getPendingBalance(): Int

    @Query("SELECT totalExpenses FROM ExpensesEntity ORDER BY ID DESC LIMIT 1")
    fun getTotalExpenses(): Int

    @Insert
    fun insertExpense(expense: ExpensesEntity)

}