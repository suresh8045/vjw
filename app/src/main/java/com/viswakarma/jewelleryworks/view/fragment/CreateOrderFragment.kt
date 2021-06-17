package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.model.util.isValid
import com.viswakarma.jewelleryworks.viewmodel.CreateOrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : BaseFragment() {
    private val viewModel by viewModel<CreateOrderViewModel>()

    private lateinit var customerSelection: AutoCompleteTextView
    private lateinit var customerSelectionInputLayout: TextInputLayout
    private lateinit var metalRadioGroup: RadioGroup
    private lateinit var modelNoInput: TextInputEditText
    private lateinit var modelNoInputLayout: TextInputLayout
    private lateinit var descriptionInput: TextInputEditText
    private lateinit var descriptionInputLayout: TextInputLayout
    private lateinit var weightInput: TextInputEditText
    private lateinit var weightInputLayout: TextInputLayout
    private lateinit var makingChargeInput: TextInputEditText
    private lateinit var makingChargeInputLayout: TextInputLayout
    private lateinit var totalAmountInput: TextInputEditText
    private lateinit var totalAmountInputLayout: TextInputLayout
    private lateinit var createOrderBtn: Button

    companion object{
        const val CUSTOMER_ID ="CUSTOMER_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_order_fragment, container, false)
    }

    override fun setupView(view: View) {
        customerSelection = view.findViewById(R.id.customerSelection)
        customerSelectionInputLayout = view.findViewById(R.id.customerSelectionLayout)
        metalRadioGroup = view.findViewById(R.id.radioGroupMetal)
        modelNoInput = view.findViewById(R.id.modelno)
        modelNoInputLayout = view.findViewById(R.id.modelnoLayout)
        descriptionInput = view.findViewById(R.id.description)
        descriptionInputLayout = view.findViewById(R.id.descriptionLayout)
        weightInput = view.findViewById(R.id.weight)
        weightInputLayout = view.findViewById(R.id.weightLayout)
        makingChargeInput = view.findViewById(R.id.makingCharge)
        makingChargeInputLayout = view.findViewById(R.id.makingChargeLayout)
        totalAmountInput = view.findViewById(R.id.amount)
        totalAmountInputLayout = view.findViewById(R.id.amountLayout)
        createOrderBtn = view.findViewById(R.id.save_btn)
    }

    override fun setupListeners(view: View) {
        metalRadioGroup.setOnCheckedChangeListener { group, _ ->
            doMetalSelectionValidation(group)
        }
        modelNoInput.doAfterTextChanged {
            doModelNoValidation(it.toString(), modelNoInputLayout)
        }
        descriptionInput.doAfterTextChanged {
            doDescriptionValidation(it.toString(), descriptionInputLayout)
        }
        weightInput.doAfterTextChanged {
            doWeightValidation(it.toString(), weightInputLayout)
        }
        makingChargeInput.doAfterTextChanged {
            doMakingChargeValidation(it.toString(), makingChargeInputLayout)
        }
        totalAmountInput.doAfterTextChanged {
            doTotalAmountValidation(it.toString(), totalAmountInputLayout)
        }
        createOrderBtn.setOnClickListener {
            doCustomerValidation(customerSelectionInputLayout)
            doMetalSelectionValidation(metalRadioGroup)
            doModelNoValidation(modelNoInput.text.toString(), modelNoInputLayout)
            doDescriptionValidation(descriptionInput.text.toString(), descriptionInputLayout)
            doWeightValidation(weightInput.text.toString(), weightInputLayout)
            doMakingChargeValidation(makingChargeInput.text.toString(), makingChargeInputLayout)
            doTotalAmountValidation(totalAmountInput.text.toString(), totalAmountInputLayout)
            if (metalRadioGroup.findViewById<RadioButton>(R.id.radio_button_silver).error.isNullOrBlank()
                && modelNoInputLayout.error.isNullOrBlank()
                && descriptionInputLayout.error.isNullOrBlank()
                && weightInputLayout.error.isNullOrBlank()
                && makingChargeInputLayout.error.isNullOrBlank()
                && totalAmountInputLayout.error.isNullOrBlank()
            ) {
                viewModel.createOrder(
                    customerId = "customerId",
                    name = "",
                    "phone",
                    descriptionInput.text.toString(),
                    totalAmountInput.text.toString().toIntOrNull() ?: 0
                )
                viewModel.getIsSumbitted().observe(viewLifecycleOwner) {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun setupObservers() {
        val customerId = arguments?.getString(CUSTOMER_ID,"")
        viewModel.getCustomersList(customerId).observe(viewLifecycleOwner){ customers ->
            val items = customers.map { "${it.phone}|${it.name}" }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
            (customerSelection as? AutoCompleteTextView)?.run {
                doAfterTextChanged {
                    viewModel.setSelectedCustomer(null)
                    doCustomerValidation(customerSelectionInputLayout)
                }
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    viewModel.setSelectedCustomer(customers[position])
                    doCustomerValidation(customerSelectionInputLayout)
                }

            }
        }
    }

    private fun doMetalSelectionValidation(view: RadioGroup) {
        when (view.checkedRadioButtonId) {
            View.NO_ID -> {
                view.findViewById<RadioButton>(R.id.radio_button_silver).error = "Select Metal Type"
            }
            R.id.radio_button_gold -> {
                view.findViewById<RadioButton>(R.id.radio_button_silver).error = null
            }
            R.id.radio_button_silver -> {
                view.findViewById<RadioButton>(R.id.radio_button_silver).error = null
            }
        }
    }

    private fun doWeightValidation(text: String, layout: TextInputLayout) {
        if (text.isValid().not() && text.toDoubleOrNull() == null) {
            layout.error = "Invalid Weight"
        } else {
            layout.error = null
        }
    }

    private fun doTotalAmountValidation(text: String, layout: TextInputLayout) {
        if (text.isValid(isDigitsOnly = true).not()) {
            layout.error = "Amount must be 0 or more"
        } else {
            layout.error = null
        }
    }

    private fun doMakingChargeValidation(text: String, layout: TextInputLayout) {
        if (text.isValid(isDigitsOnly = true).not()) {
            layout.error = "Making charge must be 0 or more"
        } else {
            layout.error = null
        }
    }

    private fun doDescriptionValidation(text: String, layout: TextInputLayout) {
        if (text.isValid(maxlength = 100).not()) {
            layout.error = "Must not Empty and Max characters 100"
        } else {
            layout.error = null
        }
    }

    private fun doModelNoValidation(text: String, layout: TextInputLayout) {
        if (text.isValid().not()) {
            layout.error = "Select Model"
        } else {
            layout.error = null
        }
    }

    private fun doCustomerValidation(layout: TextInputLayout) {
        if (viewModel.getSelectedCustomer()==null) {
            layout.error = "Customer Not Selected/Found"
        } else {
            layout.error = null
        }
    }


}
