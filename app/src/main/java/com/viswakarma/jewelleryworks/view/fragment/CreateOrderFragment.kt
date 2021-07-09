package com.viswakarma.jewelleryworks.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Metal
import com.viswakarma.jewelleryworks.model.util.isValid
import com.viswakarma.jewelleryworks.viewmodel.CreateOrderViewModel
import kotlinx.android.synthetic.main.create_order_fragment.*
import kotlinx.android.synthetic.main.create_order_fragment.view.*
import kotlinx.android.synthetic.main.recycler_order_itemview.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CreateOrderFragment : BaseFragment() {
    private val viewModel by viewModel<CreateOrderViewModel>()
    private val args: CreateOrderFragmentArgs by navArgs()

    private lateinit var customerSelection: AutoCompleteTextView
    private lateinit var customerSelectionInputLayout: TextInputLayout
    private lateinit var metalRadioGroup: RadioGroup
    private lateinit var modelNoInput: AutoCompleteTextView
    private lateinit var modelNoInputLayout: TextInputLayout
    private lateinit var modelImage: ImageView
    private lateinit var customModelCheckBox: CheckBox
    private lateinit var descriptionInput: TextInputEditText
    private lateinit var descriptionInputLayout: TextInputLayout
    private lateinit var weightInput: TextInputEditText
    private lateinit var weightInputLayout: TextInputLayout
    private lateinit var createOrderBtn: Button

    companion object{
        const val customerId ="customerId"
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
        modelImage = view.modelImage
        customModelCheckBox = view.customModelCheckBox
        descriptionInput = view.findViewById(R.id.description)
        descriptionInputLayout = view.findViewById(R.id.descriptionLayout)
        weightInput = view.findViewById(R.id.weight)
        weightInputLayout = view.findViewById(R.id.weightInputLayout)
        createOrderBtn = view.findViewById(R.id.save_btn)
    }

    override fun setupListeners(view: View) {
        metalRadioGroup.setOnCheckedChangeListener { group, _ ->
            doMetalSelectionValidation(group)
        }
        descriptionInput.doAfterTextChanged {
            doDescriptionValidation(it.toString(), descriptionInputLayout)
        }
        weightInput.doAfterTextChanged {
            doWeightValidation(it.toString(), weightInputLayout)
        }
        customModelCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                viewModel.setSelectedModel(null)
                modelNoInput.text.clear()
                metalRadioGroup.clearCheck()
            }
        }

        createOrderBtn.setOnClickListener {
            doCustomerValidation(customerSelectionInputLayout)
            doMetalSelectionValidation(metalRadioGroup)
            doModelNoValidation(modelNoInput.text.toString(), modelNoInputLayout)
            doDescriptionValidation(descriptionInput.text.toString(), descriptionInputLayout)
            doWeightValidation(weightInput.text.toString(), weightInputLayout)
            if (metalRadioGroup.findViewById<RadioButton>(R.id.radio_button_silver).error.isNullOrBlank()
                && modelNoInputLayout.error.isNullOrBlank()
                && descriptionInputLayout.error.isNullOrBlank()
                && weightInputLayout.error.isNullOrBlank()
            ) {
                val customer = viewModel.getSelectedCustomer()!!
                val modelStr = if(customModelCheckBox.isChecked.not()) {
                    viewModel.getSelectedModel()!!.modelNo//let { "${it.name} |${it.modelNo}" }
                }else {
                    modelNoInput.text.toString()
                }
                viewModel.createOrder(
                    customerId = customer.id,
                    name = customer.name,
                    phone = customer.phone,
                    metal = getMetalChecked()!!,
                    model = modelStr,
                    description = descriptionInput.text.toString(),
                    weight = weightInput.text.toString().toDouble()
                )
                viewModel.getIsSumbitted().observe(viewLifecycleOwner) {
                    findNavController().navigate(CreateOrderFragmentDirections.actionNavigationCreateOrderToOrderDetailsFragment().setOrderId(viewModel.orderId))
                }
            }
        }
    }

    private fun getMetalChecked(): Metal? {
        return when (metalRadioGroup.checkedRadioButtonId) {
            R.id.radio_button_gold -> {
                Metal.GOLD
            }
            R.id.radio_button_silver -> {
                Metal.SILVER
            }
            else -> null
        }
    }

    override fun setupObservers() {
        Log.e("customerId", "${args.customerId}")
        val customerId = args.customerId
        viewModel.getCustomersList(customerId).observe(viewLifecycleOwner){ customers ->
            val items = customers.map { "${it.phone}|${it.name}" }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
            (customerSelection as? AutoCompleteTextView)?.run {
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    viewModel.setSelectedCustomer(customers[position])
                    doCustomerValidation(customerSelectionInputLayout)
                }
                if(customerId.isNotBlank()){
                    customerSelection.setText(items[0])
                    viewModel.setSelectedCustomer(customers[0])
                }
                doAfterTextChanged {
                    viewModel.setSelectedCustomer(null)
                    doCustomerValidation(customerSelectionInputLayout)
                }
            }
        }
        viewModel.allCatalogueItems.observe(viewLifecycleOwner){ models: List<Catalogue> ->
            val items = models.map { "${it.name}|${it.modelNo}" }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
            (modelNoInput as? AutoCompleteTextView)?.run {
                setAdapter(adapter)
                setOnItemClickListener { parent, view, position, id ->
                    Timber.d("pos:$position model ")
                    val item = adapter.getItem(position)
                    val catalogue = models.first { item ==  "${it.name}|${it.modelNo}" }
                    viewModel.setSelectedModel(catalogue)//models[position])
                    doModelNoValidation(modelNoInput.text.toString(), modelNoInputLayout)
                }
                doAfterTextChanged {
                    viewModel.setSelectedModel(null)
                    doModelNoValidation(modelNoInput.text.toString(), modelNoInputLayout)
                }
            }
        }
        viewModel.selectedModel.observe(viewLifecycleOwner){ model: Catalogue? ->
            if(model!=null){
                val decodedString = Base64.decode(model.image, Base64.NO_WRAP)
                modelImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0,decodedString.size))
                customModelCheckBox.isChecked = false
                when(model.metal){
                    Metal.GOLD.value -> metalRadioGroup.check(R.id.radio_button_gold)
                    Metal.SILVER.value -> metalRadioGroup.check(R.id.radio_button_silver)
                    else -> {metalRadioGroup.clearCheck()}
                }
                weightInput.setText(model.weight.toString())
            }else{
                modelImage.setImageResource(R.drawable.ic_baseline_photo_library_24)
                metalRadioGroup.clearCheck()
                weightInput.text?.clear()
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

    private fun doDescriptionValidation(text: String, layout: TextInputLayout) {
        if (text.isValid(maxlength = 100).not()) {
            layout.error = "Must not Empty and Max characters 100"
        } else {
            layout.error = null
        }
    }

    private fun doModelNoValidation(text: String, layout: TextInputLayout) {
        if(customModelCheckBox.isChecked){
            if (text.isValid(maxlength = 64).not()) {
                layout.error = "Required Custom Model, MAX 64 chars"
            } else {
                layout.error = null
            }
        }else {
            if (viewModel.getSelectedModel() == null) {
                layout.error = "Select Model"
            } else {
                layout.error = null
            }
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
