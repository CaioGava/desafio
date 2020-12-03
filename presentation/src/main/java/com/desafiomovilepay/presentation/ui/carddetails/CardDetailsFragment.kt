package com.desafiomovilepay.presentation.ui.carddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.desafiomovilepay.presentation.databinding.CardDetailsFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CardDetailsFragment : Fragment() {
    private lateinit var binding: CardDetailsFragmentBinding
    private val viewModel: CardDetailsViewModel by viewModels()
    private val args: CardDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CardDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is CardDetailsViewModel.State.Success -> {
                        updateInfoAtView()
                    }
                    is CardDetailsViewModel.State.Error -> {
                        Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
                    }
                    is CardDetailsViewModel.State.Loading -> {
                        Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        viewModel.requestCardDetails(args.cardId)
    }

    private fun updateInfoAtView() {
        val state = viewModel.state.value as CardDetailsViewModel.State.Success
        state.cardDetails.apply {
            binding.tvCardNumber.text = cardNumber
            binding.tvCardName.text = cardName
            binding.tvExpirationValue.text = expirationDate
            binding.tvAvailableLimitValue.text = availableLimit
            binding.tvTotalLimitValue.text = totalLimit
        }
        binding.root.visibility = View.VISIBLE
    }
}