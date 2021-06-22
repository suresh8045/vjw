package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.view.adapter.DashboardAdapter
import com.viswakarma.jewelleryworks.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment(){

    private lateinit var ordersBtn: Button
    private val dashboardViewModel by viewModel<DashboardViewModel>()

    private lateinit var dashboardRecyclerView: RecyclerView
    private lateinit var dashboardAdapter: DashboardAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupObservers()
    }

    override fun setupView(view: View) {
        dashboardRecyclerView = view.findViewById(R.id.dashboard_recyclerview)
        ordersBtn = view.findViewById(R.id.ordersBtn)
        dashboardRecyclerView.layoutManager = LinearLayoutManager(context)
        dashboardAdapter = DashboardAdapter(requireContext(),object : DashboardAdapter.OnDashboardItemsInteractionListener{

        }) {

        }
        dashboardRecyclerView.adapter = dashboardAdapter
    }

    override fun setupListeners(view: View) {
        ordersBtn.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToOrdersFragment())
        }
    }

    override fun setupObservers() {
        dashboardViewModel.getAllOrders().observe(viewLifecycleOwner) { list ->
            loadRecyclerViewList(list)
        }
    }

    private fun loadRecyclerViewList(newList: List<Any>) {
        dashboardAdapter.submitList(newList)
        dashboardAdapter.notifyDataSetChanged()
    }

}
