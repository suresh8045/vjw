package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import org.koin.android.ext.android.inject

class AddCustomerFragment : BaseFragment() {

    private lateinit var customerName: TextInputEditText
    private lateinit var phoneNo: TextInputEditText
    private lateinit var address: TextInputEditText
    private lateinit var addCustomerButton: Button
    private val viewModel: AddCustomerViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_customer_fragment, container, false)
    }

    override fun setupView(view: View) {
        customerName = view.findViewById(R.id.name)
        phoneNo = view.findViewById(R.id.phone)
        address = view.findViewById(R.id.address)
        addCustomerButton = view.findViewById(R.id.addCustomerButton)
    }

    override fun setupListeners(view: View) {
        customerName.doAfterTextChanged {
            doCustomerValidation(it.toString())
        }
        phoneNo.doAfterTextChanged {
            doPhoneNoValidation(it.toString())
        }
        address.doAfterTextChanged {
            doAddressValidation(it.toString())
        }
        addCustomerButton.setOnClickListener {
            doCustomerValidation(customerName.text.toString())
            doPhoneNoValidation(phoneNo.text.toString())
            doAddressValidation(address.text.toString())
            if (customerName.error.isNullOrBlank() && phoneNo.error.isNullOrBlank() && address.error.isNullOrBlank()) {
                viewModel.addCustomer(
                    customerName.text.toString(),
                    phoneNo.text.toString(),
                    address.text.toString(),
                )
            }
        }
    }

    private fun doCustomerValidation(customerName: String) {
        if (viewModel.isCustomerNameValid(customerName).not())
            this.customerName.error = "Invalid Name"
    }

    private fun doAddressValidation(address: String) {
        if (viewModel.isAddressValid(address).not())
            this.address.error = "Invalid Address"
    }

    private fun doPhoneNoValidation(phoneNo: String) {
        if (viewModel.isPhoneNoValid(phoneNo).not())
            this.phoneNo.error = "Invalid Phone Number"
    }


    override fun setupObservers() {
        viewModel.addCustomerInProgress.observe(viewLifecycleOwner) { inProgress ->
            addCustomerButton.isEnabled = !inProgress
        }
        viewModel.addCustomerIsFinished.observe(viewLifecycleOwner) { isFinished ->
            if (isFinished) findNavController().navigateUp()
        }
    }

}