package com.internshala.expenses.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExpensesEntity")
data class ExpensesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "fullName") var fullName: String?,
    @ColumnInfo(name = "mobileNumber") var mobileNumber: String?,
    @ColumnInfo(name = "category") var category: String?,
    @ColumnInfo(name = "date") var date: String?,
    @ColumnInfo(name = "invoiceNumber") var invoiceNumber: Int?,
    @ColumnInfo(name = "balancePendingAmount") var balancePendingAmount: Int?,
    @ColumnInfo(name = "pendingBalance") val totalPendingBalance: Int?,
    @ColumnInfo(name = "totalExpenses") var totalExpenses: Int?,
    @ColumnInfo(name = "paidAmount") var paidAmount: Int?,
    @ColumnInfo(name = "totalToPay") var totalToPay: Int?
)