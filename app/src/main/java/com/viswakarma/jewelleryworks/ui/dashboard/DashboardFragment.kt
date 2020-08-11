package com.viswakarma.jewelleryworks.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.di.ViswakarmaViewModelFactory
import com.viswakarma.jewelleryworks.ui.dashboard.adapters.DashboardAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DashboardFragment : DaggerFragment(),DashboardAdapter.OnDashboardItemsInteractionListener {

    private lateinit var dashboardRecyclerView: RecyclerView
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var contxt: Context
    @Inject
    lateinit var viewModelFactory: ViswakarmaViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contxt = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this, viewModelFactory).get(DashboardViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardRecyclerView = view.findViewById(R.id.dashboard_recyclerview)
        dashboardRecyclerView.layoutManager = LinearLayoutManager(context)
        dashboardAdapter = DashboardAdapter(contxt,this) {model->

        }
        dashboardRecyclerView.adapter = dashboardAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        dashboardViewModel.getAllOrders().observe(viewLifecycleOwner, Observer {list->
            loadRecyclerViewList(list)
        })
    }


    private fun loadRecyclerViewList(newList: List<Any>) {
        dashboardAdapter.submitList(newList)
        dashboardAdapter.notifyDataSetChanged()
    }

}
