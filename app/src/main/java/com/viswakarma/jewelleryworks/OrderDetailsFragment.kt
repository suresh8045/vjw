package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.viswakarma.jewelleryworks.view.adapter.DataAdapter
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsFragment : BaseFragment() {

    private lateinit var fab: FloatingActionButton
    private lateinit var dataAdapter: DataAdapter
    private lateinit var transactionsRecyclerView: RecyclerView
    private val viewModel: OrderDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun setupView(view: View) {
        fab = view.findViewById(R.id.fab)
        transactionsRecyclerView = view.findViewById(R.id.transactionsRecyclerView)
        transactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataAdapter = DataAdapter(requireContext())
        transactionsRecyclerView.adapter = dataAdapter
    }

    override fun setupListeners(view: View) {
        fab.setOnClickListener {
            findNavController().navigate(OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddTransactionFragment())
        }
    }

    override fun setupObservers() {
        viewModel.transactions.observe(viewLifecycleOwner){
            dataAdapter.submitList(it)
        }
    }

}