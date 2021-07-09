package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.view.adapter.DataAdapter
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersFragment : BaseFragment() {

    private lateinit var dataAdapter: DataAdapter
    private lateinit var ordersRecyclerView: RecyclerView
    private val viewModel: OrdersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun setupView(view: View) {
        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataAdapter = DataAdapter()
        ordersRecyclerView.adapter = dataAdapter
    }

    override fun setupListeners(view: View) {
    }

    override fun setupObservers() {
        viewModel.orders.observe(viewLifecycleOwner){ orders ->
            dataAdapter.submitList(orders)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val actionSearch = menu.findItem(R.id.action_search)
        actionSearch.isVisible=true
        (actionSearch.actionView as? SearchView)?.run {
            setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.setSearchQuery(newText?:"")
                    return true
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_add -> {
//            findNavController().navigate(CatalogueFragmentDirections.actionNavigationCatalogueToAddToCatalogueFragment())
            true
        }
        R.id.action_search -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }




}