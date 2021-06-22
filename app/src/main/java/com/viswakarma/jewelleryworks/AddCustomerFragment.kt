package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import org.koin.android.ext.android.inject

class AddCustomerFragment : BaseFragment() {

    private lateinit var customerName: TextInputEditText
    private lateinit var customerNameLayout: TextInputLayout
    private lateinit var phoneNo: TextInputEditText
    private lateinit var phoneNoLayout: TextInputLayout
    private lateinit var address: TextInputEditText
    private lateinit var addressLayout: TextInputLayout
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
        customerNameLayout = view.findViewById(R.id.customerNameLayout)
        phoneNo = view.findViewById(R.id.phone)
        phoneNoLayout = view.findViewById(R.id.phoneLayout)
        address = view.findViewById(R.id.address)
        addressLayout = view.findViewById(R.id.addressInputLayout)
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
            if (customerNameLayout.error.isNullOrBlank() && phoneNoLayout.error.isNullOrBlank() && addressLayout.error.isNullOrBlank()) {
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
            this.customerNameLayout.error = "Invalid Name"
        else
            this.customerNameLayout.error = null
    }

    private fun doAddressValidation(address: String) {
        if (viewModel.isAddressValid(address).not())
            this.addressLayout.error = "Invalid Address"
        else
            this.addressLayout.error = null
    }

    private fun doPhoneNoValidation(phoneNo: String) {
        if (viewModel.isPhoneNoValid(phoneNo).not())
            this.phoneNoLayout.error = "Invalid Phone Number"
        else
            this.phoneNoLayout.error = null
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