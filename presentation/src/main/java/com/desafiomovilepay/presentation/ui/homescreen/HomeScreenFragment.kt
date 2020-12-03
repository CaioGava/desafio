package com.desafiomovilepay.presentation.ui.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.desafiomovilepay.presentation.databinding.FragmentHomeScreenBinding
import com.desafiomovilepay.presentation.ui.homescreen.widgetlist.WidgetsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var widgetsAdapter: WidgetsAdapter
    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createObservers()
        viewModel.getWidgets()
    }

    private fun initRecyclerView() {
        widgetsAdapter = WidgetsAdapter(viewModel.allWidgets)
        binding.rvWidgets.adapter = widgetsAdapter
        binding.rvWidgets.layoutManager = LinearLayoutManager(this.context)
    }

    private fun createObservers() {
        viewModel.allWidgets.observe(
            viewLifecycleOwner,
            { _ -> widgetsAdapter.notifyDataSetChanged() }
        )
    }
}