package com.internshala.expenses

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.internshala.expenses.data.ExpenseViewModel
import com.internshala.expenses.data.ExpensesEntity
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@DelicateCoroutinesApi
class AddExpense : AppCompatActivity() {

    private lateinit var intentHome: Intent
    private lateinit var vmAddExpense: ExpenseViewModel

    private lateinit var btnSave: Button
    private lateinit var btnAdd: ImageButton

    private lateinit var btnDateEdit: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvBalance: TextView

    private lateinit var etInvoiceNumber: EditText
    private lateinit var etFullName: EditText
    private lateinit var etMobileNumber: EditText
    private lateinit var etCategory: EditText
    private lateinit var etTotal: EditText
    private lateinit var etPaid: EditText

    private val calender = Calendar.getInstance()
    private val year = calender[Calendar.YEAR]
    private val month = calender[Calendar.MONTH]
    private val day = calender[Calendar.DAY_OF_MONTH]



    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)


        btnSave = findViewById(R.id.btn_save)
        btnAdd = findViewById(R.id.btn_back)
        btnDateEdit = findViewById(R.id.tv_edit_date)
        tvDate = findViewById(R.id.tv_date)
        tvBalance = findViewById(R.id.tv_remaining)
        etInvoiceNumber = findViewById(R.id.et_invoice_number)
        etFullName = findViewById(R.id.et_name)
        etMobileNumber = findViewById(R.id.et_mobile_number)
        etCategory = findViewById(R.id.et_category)
        etTotal = findViewById(R.id.et_total)
        etPaid = findViewById(R.id.et_paid_amount)

        getDate()

        btnAdd.setOnClickListener {
            backToHome()
        }

        btnDateEdit.setOnClickListener {
            editDate()
        }

        btnSave.setOnClickListener {
            if (checkEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    addExpense()
                }
            }

        }

    }


    private fun backToHome() {
        intentHome = Intent(this, MainActivity::class.java)
        startActivity(intentHome)
        finish()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate() {

        val current = LocalDateTime.now()
        val formatterDay = DateTimeFormatter.ofPattern("dd")
        val formatterMonth = DateTimeFormatter.ofPattern("MM")
        val formatterYear = DateTimeFormatter.ofPattern("yyyy")
        val formattedDay = current.format(formatterDay)
        val formattedMonth = current.format(formatterMonth)
        val formattedYear = current.format(formatterYear)
        tvDate.text =
            "${getString(R.string.date)}\t\t$formattedDay ${getMonthName(formattedMonth.toInt())} $formattedYear "
    }

    @SuppressLint("SetTextI18n")
    private fun editDate() {


        val dateDialog = DatePickerDialog(
            this, R.style.date_picker,
            { _, _, _, dayOfMonth ->
                tvDate.text =
                    "${getString(R.string.date)}\t\t$dayOfMonth ${getMonthName(month)} $year"
            }, year, month, day
        )
        dateDialog.show()
    }

    private fun getMonthName(month: Int): String {
        if (month == 1) return "January "
        if (month == 2) return "February "
        if (month == 3) return "March"
        if (month == 4) return "April"
        if (month == 5) return "May"
        if (month == 6) return "June"
        if (month == 7) return "July"
        if (month == 8) return "August"
        if (month == 9) return "September"
        if (month == 10) return "October"
        if (month == 11) return "November"
        return if (month == 12) "December" else "JAN"
    }

    private fun checkEmpty(): Boolean {
        when {
            etInvoiceNumber.text.toString().isEmpty() -> {
                etInvoiceNumber.error = "Empty"
                return false
            }
            etFullName.text.toString().isEmpty() -> {
                etFullName.error = "Empty"
                return false
            }
            etMobileNumber.text.toString().isEmpty() -> {
                etMobileNumber.error = "Empty"
                return false
            }
            etCategory.text.toString().isEmpty() -> {
                etCategory.error = "Empty"
                return false

            }
            etTotal.text.toString().isEmpty() -> {
                etTotal.error = "Empty"
                return false
            }
            etPaid.text.toString().isEmpty() -> {
                etPaid.error = "Empty"
                return false
            }
            else -> {
                return true
            }
        }
    }

    private suspend fun addExpense() {
        vmAddExpense = ViewModelProvider(this)[ExpenseViewModel::class.java]


        val expense = vmAddExpense.getTotalExpenses
        val balance = vmAddExpense.getBalance
        val newExpense: ExpensesEntity
        withContext(Dispatchers.Main) {
            tvBalance.text = "${etTotal.text.toString().toInt() - etPaid.text.toString().toInt()}"
            newExpense = ExpensesEntity(
                0,
                etFullName.text.toString(),
                etMobileNumber.text.toString(),
                etCategory.text.toString(),
                "$day\n${getMonthName(month)}\n$year",
                etInvoiceNumber.text.toString().toInt(),
                tvBalance.text.toString().toInt(),
                tvBalance.text.toString().toInt() + balance,
                etPaid.text.toString().toInt() + expense,
                etPaid.text.toString().toInt(),
                etTotal.text.toString().toInt()
            )
        }
        vmAddExpense.addExpense(newExpense)
        withContext(Dispatchers.Main) {
            Toast.makeText(applicationContext, "Expenses Added", Toast.LENGTH_LONG).show()
        }
    }
}