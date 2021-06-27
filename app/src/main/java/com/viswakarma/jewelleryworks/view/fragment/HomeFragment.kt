package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.view.adapter.DataAdapter
import com.viswakarma.jewelleryworks.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private lateinit var dataAdapter: DataAdapter
    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var customersRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun setupView(view: View) {
        customersRecyclerView = view.findViewById(R.id.topCustomersRecyclerView)
        customersRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dataAdapter = DataAdapter(requireContext())
        customersRecyclerView.adapter = dataAdapter
    }

    override fun setupListeners(view: View) {

    }

    override fun setupObservers() {
        viewModel.topCustomers.observe(viewLifecycleOwner) { customers ->
            dataAdapter.submitList(customers)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val actionDone = menu.findItem(R.id.action_done)
        actionDone.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_add -> {
            showMenu(requireView(), R.menu.popup_menu_add)
            true
        }

        R.id.action_search -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.add_customer ->{
                    findNavController().navigate(R.id.action_navigation_home_to_addCustomerFragment)
                    true
                }
                R.id.create_order ->{
                    findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNavigationCreateOrder())
                    true
                }
                else -> false
            }
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }


}
