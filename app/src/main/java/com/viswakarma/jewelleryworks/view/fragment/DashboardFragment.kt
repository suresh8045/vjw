package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.view.adapter.DashboardAdapter
import com.viswakarma.jewelleryworks.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(){

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

    private fun setupView(view: View) {
        dashboardRecyclerView = view.findViewById(R.id.dashboard_recyclerview)
        dashboardRecyclerView.layoutManager = LinearLayoutManager(context)
        dashboardAdapter = DashboardAdapter(requireContext(),object : DashboardAdapter.OnDashboardItemsInteractionListener{

        }) {

        }
        dashboardRecyclerView.adapter = dashboardAdapter
    }

    private fun setupObservers() {
        dashboardViewModel.getAllOrders().observe(viewLifecycleOwner) { list ->
            loadRecyclerViewList(list)
        }
    }

    private fun loadRecyclerViewList(newList: List<Any>) {
        dashboardAdapter.submitList(newList)
        dashboardAdapter.notifyDataSetChanged()
    }

}
