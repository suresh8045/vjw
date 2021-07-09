package com.viswakarma.jewelleryworks

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.viswakarma.jewelleryworks.view.adapter.DataAdapter
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.create_order_fragment.view.modelImage
import kotlinx.android.synthetic.main.fragment_order_details.view.*
import kotlinx.android.synthetic.main.order_details_section.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class OrderDetailsFragment : BaseFragment() {

    private lateinit var customerName: TextView
    private lateinit var phoneNo: TextView
    private lateinit var modelImage: ImageView
    private lateinit var orderId: TextView
    private lateinit var orderName: TextView
    private lateinit var totalAmountCharges: TextView
    private lateinit var totalPaidAmount: TextView
    private lateinit var totalAmountDue: TextView
    private lateinit var fab: ExtendedFloatingActionButton
    private lateinit var dataAdapter: DataAdapter
    private lateinit var transactionsRecyclerView: RecyclerView
    private val viewModel: OrderDetailsViewModel by viewModel()
    private val args: OrderDetailsFragmentArgs by navArgs()
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                dialPhoneNumber(phoneNo.text.toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    "Unable to make Call, permission denied",
                    Toast.LENGTH_SHORT
                ).show()
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }

    override fun setupView(view: View) {
        customerName = view.customerName
        phoneNo = view.phoneNo
        modelImage = view.modelImage
        fab = view.findViewById(R.id.fab)
        orderId = view.orderId
        orderName = view.orderName
        totalAmountCharges = view.totalAmountCharges
        totalPaidAmount = view.totalPaidAmount
        totalAmountDue = view.totalAmountDue
        transactionsRecyclerView = view.findViewById(R.id.transactionsRecyclerView)
        transactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataAdapter = DataAdapter()
        transactionsRecyclerView.adapter = dataAdapter
    }

    override fun setupListeners(view: View) {
        fab.setOnClickListener {
            findNavController().navigate(OrderDetailsFragmentDirections.actionOrderDetailsFragmentToAddTransactionFragment().setOrderId(args.orderId))
        }
    }

    override fun setupObservers() {
        viewModel.orderDetails(args.orderId).observe(viewLifecycleOwner){ orderDetails ->
            if(orderDetails!=null) {
                customerName.text = orderDetails.order.name
                phoneNo.text = orderDetails.order.phone
                orderDetails.catalogue?.let { catalogue ->
                    val decodedString = Base64.decode(catalogue.image, Base64.NO_WRAP)
                    modelImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0,decodedString.size))
                }
                orderId.text = args.orderId
                orderName.text = orderDetails.catalogue?.name?:orderDetails.order.modelNo
                totalAmountCharges.text = orderDetails.getTotalCharges().toString()
                totalPaidAmount.text = orderDetails.getTotalPaidAmount().toString()
                totalAmountDue.text = orderDetails.getTotalDueAmount().toString()
                dataAdapter.submitList(orderDetails.transactions)
            }
        }
//        viewModel.transactions.observe(viewLifecycleOwner){
//            dataAdapter.submitList(it)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val actionCall = menu.findItem(R.id.action_call)
        actionCall.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_call ->{
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        Timber.d("dialPhone")
        startActivity(intent)
        if (intent.resolveActivity(requireContext().packageManager) != null) {

        }
    }
}