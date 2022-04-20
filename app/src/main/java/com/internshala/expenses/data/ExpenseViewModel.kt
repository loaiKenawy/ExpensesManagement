package com.internshala.expenses.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    val getAllExpenses: LiveData<List<ExpensesEntity>>
    val getBalance: Int
    val getTotalExpenses: Int
    private val repo: ExpensesRepository

    init {
        val expensesDao = ExpensesDatabase.getExpensesDatabase(application).expensesDao()
        repo = ExpensesRepository(expensesDao)
        getAllExpenses = repo.getAllExpenses
        getBalance = repo.getBalance
        getTotalExpenses = repo.getTotalExpenses
    }

    fun addExpense(expense: ExpensesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addExpenses(expense)
        }
    }
}