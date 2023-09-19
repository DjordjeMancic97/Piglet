package com.djordjemancic.piglet.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.djordjemancic.piglet.R
import com.djordjemancic.piglet.databinding.FragmentHomeBinding
import com.djordjemancic.piglet.other.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerManager: RecyclerView.LayoutManager
    private val recyclerAdapter: TransactionListAdapter = TransactionListAdapter(
        emptyList()
    )
    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.transactionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collect { resource ->
                    if(resource.status == Resource.Status.SUCCESS) {
                        recyclerAdapter.setData(resource.data!!.transactions)
                        println("FROM COROUTINE ${resource.data.transactions}")
                    }
                    println("FROM COROUTINE ${resource.data?.transactions} ${resource.status}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}