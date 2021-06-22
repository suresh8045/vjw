package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.view.adapter.DataAdapter
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : BaseFragment() {

    private lateinit var dataAdapter: DataAdapter
    private lateinit var ordersRecyclerView: RecyclerView
    private val viewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun setupView(view: View) {
        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataAdapter = DataAdapter(requireContext())
        ordersRecyclerView.adapter = dataAdapter
    }

    override fun setupListeners(view: View) {
    }

    override fun setupObservers() {
        viewModel.orders.observe(viewLifecycleOwner){ orders ->
            dataAdapter.submitList(orders)
        }
    }


}