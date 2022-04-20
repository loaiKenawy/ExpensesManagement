package com.internshala.expenses.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.expenses.R
import com.internshala.expenses.data.ExpensesEntity

class ExpenseAdapter(private val expensesDataset: List<ExpensesEntity>) :

    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // define the views obj
        val tvDate: TextView = view.findViewById(R.id.tv_date)
        val tvPaidAmount: TextView = view.findViewById(R.id.tv_paid_amount)
        val tvName: TextView = view.findViewById(R.id.tv_expense_name)
        val tvCategory: TextView = view.findViewById(R.id.tv_expense_category)
        val tvTotalAmount: TextView = view.findViewById(R.id.tv_total_amount)
        val tvDueAmount: TextView = view.findViewById(R.id.tv_due_amount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.expenses_item_view, parent, false)
        return ExpenseViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.tvDate.text = expensesDataset[position].date
        holder.tvPaidAmount.text = "Paid Rs." + expensesDataset[position].paidAmount.toString()
        holder.tvName.text = expensesDataset[position].fullName
        holder.tvCategory.text = expensesDataset[position].category
        holder.tvTotalAmount.text = "Rs." + expensesDataset[position].totalToPay.toString()
        holder.tvDueAmount.text =
            "Due Rs." + expensesDataset[position].balancePendingAmount.toString()
    }

    override fun getItemCount(): Int {
        return expensesDataset.size
    }
}