package com.viswakarma.jewelleryworks.ui.createorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.di.ViswakarmaViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CreateOrderFragment : DaggerFragment() {

    companion object {
        fun newInstance() = CreateOrderFragment()
    }

    private lateinit var viewModel: CreateOrderViewModel

    @Inject
    lateinit var viewModelFactory: ViswakarmaViewModelFactory

    private lateinit var customerName: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var totalAmount: TextInputEditText
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this,viewModelFactory).get(CreateOrderViewModel::class.java)
        return inflater.inflate(R.layout.create_order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customerName = view.findViewById(R.id.name)
        phone = view.findViewById(R.id.phone)
        description = view.findViewById(R.id.description)
        totalAmount = view.findViewById(R.id.amount)
        saveBtn = view.findViewById(R.id.save_btn)
        saveBtn.setOnClickListener {
            viewModel.createOrder(customerName.text.toString(), phone.text.toString(), description.text.toString(), totalAmount.text.toString().toInt())
            viewModel.getIsSumbitted().observe(viewLifecycleOwner,Observer{
                findNavController().popBackStack()
            })
        }
    }


}
