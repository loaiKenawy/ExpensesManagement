package com.internshala.expenses

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.internshala.expenses.adapter.ExpenseAdapter
import com.internshala.expenses.data.ExpenseViewModel
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var rvExpenses: RecyclerView
    private lateinit var expensesAdapter: ExpenseAdapter
    private lateinit var btnAdd: Button
    private lateinit var intentAdd: Intent
    private lateinit var vmGetExpenses: ExpenseViewModel
    private lateinit var tvBalance: TextView
    private lateinit var tvExpenses: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch(Dispatchers.IO) {
            getAllExpenses()
            withContext(Dispatchers.Main) {
                initExpensesRV()
                getTotalBalance()
            }
        }

        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            startAddExpense()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun initExpensesRV() {
        delay(200)
        vmGetExpenses.getAllExpenses.observe(this, Observer { expenses ->
            if (expenses != null) {
                rvExpenses = findViewById(R.id.rv_expenses)
                expensesAdapter = ExpenseAdapter(expenses)
                rvExpenses.layoutManager = LinearLayoutManager(this)
                rvExpenses.adapter = expensesAdapter
                expensesAdapter.notifyDataSetChanged()
            }
        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllExpenses() {
        vmGetExpenses = ViewModelProvider(this)[ExpenseViewModel::class.java]


    }

    private fun startAddExpense() {
        intentAdd = Intent(this, AddExpense::class.java)
        startActivity(intentAdd)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private suspend fun getTotalBalance() {
        delay(200)
        tvBalance = findViewById(R.id.tv_balance_view)
        tvExpenses = findViewById(R.id.tv_expenses_month)

        tvBalance.text = "${getString(R.string.money)} ${vmGetExpenses.getBalance}"
        tvExpenses.text = "${getString(R.string.money)} ${vmGetExpenses.getTotalExpenses}"

    }
}