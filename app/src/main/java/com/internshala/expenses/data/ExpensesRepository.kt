package com.internshala.expenses.data

import androidx.lifecycle.LiveData

class ExpensesRepository(private val expensesDao: ExpensesDao) {
    val getAllExpenses: LiveData<List<ExpensesEntity>> = expensesDao.getAllExpenses()
    val getBalance: Int = expensesDao.getPendingBalance()
    val getTotalExpenses: Int = expensesDao.getTotalExpenses()

    fun addExpenses(expense: ExpensesEntity) {
        expensesDao.insertExpense(expense)
    }
}