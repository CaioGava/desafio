package com.desafiomovilepay.presentation.ui.statementdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.desafiomovilepay.presentation.databinding.StatementDetailsFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StatementDetailsFragment : Fragment() {
    private lateinit var binding: StatementDetailsFragmentBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private val viewModel: StatementDetailsViewModel by viewModels()
    private val args: StatementDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatementDetailsFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is StatementDetailsViewModel.State.Success -> {
                        updateInfoAtView()
                    }
                    is StatementDetailsViewModel.State.Error -> {
                        Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
                    }
                    is StatementDetailsViewModel.State.Loading -> {
                        Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        initRecyclerView()

        viewModel.requestStatementDetails(args.accountId)

        return binding.root
    }

    private fun updateInfoAtView() {
        val state = viewModel.state.value as StatementDetailsViewModel.State.Success
        binding.tvAvailableBalanceLabel.text = state.details.balance.label
        binding.tvAvailableBalanceValue.text = state.details.balance.value
        transactionAdapter.transactions = state.details.transactions.toMutableList()
        transactionAdapter.notifyItemInserted()

        binding.root.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        transactionAdapter = TransactionAdapter(mutableListOf())
        binding.rvTransactions.adapter = transactionAdapter
        binding.rvTransactions.layoutManager =
            LinearLayoutManager(binding.root.context)
    }

}