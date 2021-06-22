package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.view.adapter.DataAdapter
import com.viswakarma.jewelleryworks.viewmodel.CatalogueViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogueFragment : BaseFragment() {

    private lateinit var dataAdapter: DataAdapter
    private val viewModel by viewModel<CatalogueViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun setupView(view: View) {
        view.findViewById<RecyclerView>(R.id.catalogueRecyclerView).run {
            layoutManager = LinearLayoutManager(requireContext())
            dataAdapter = DataAdapter(requireContext())
            adapter = dataAdapter
        }
    }

    override fun setupListeners(view: View) {

    }

    override fun setupObservers() {
        viewModel.catalogueItems.observe(viewLifecycleOwner){
            dataAdapter.submitList(it)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val actionDone = menu.findItem(R.id.action_done)
        actionDone.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_add -> {
            findNavController().navigate(CatalogueFragmentDirections.actionNavigationCatalogueToAddToCatalogueFragment())
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