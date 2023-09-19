package com.djordjemancic.piglet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djordjemancic.piglet.data.dto.Transaction
import com.djordjemancic.piglet.databinding.HomeTransactionCardBinding

class TransactionListAdapter(private var transactionList: List<Transaction>) : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val transactionBinding = HomeTransactionCardBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(transactionBinding)

    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactionList[position])
    }

    class TransactionViewHolder(private val binding: HomeTransactionCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.transaction = transaction
        }
    }

    fun setData(list: List<Transaction>) {
        this.transactionList = list
        notifyDataSetChanged()
    }

}

