package com.desafiomovilepay.presentation.ui.statementdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desafiomovilepay.presentation.databinding.ListItemTransactionBinding
import com.desafiomovilepay.presentation.model.StatementDetails

class TransactionAdapter(
    var transactions: MutableList<StatementDetails.Transaction>
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(private val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: StatementDetails.Transaction) {
            binding.tvTransactionValue.text = transaction.label
            binding.tvTransactionMoneyValue.text = transaction.value
            binding.tvTransactionDescription.text = transaction.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding =
            ListItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentTransaction = transactions[position]

        holder.bind(currentTransaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun notifyItemInserted() {
        transactions.size.minus(1).let { notifyItemInserted(it) }
    }

}
