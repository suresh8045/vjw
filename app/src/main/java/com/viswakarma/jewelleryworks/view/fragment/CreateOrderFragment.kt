package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.viewmodel.CreateOrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateOrderFragment : Fragment() {

    private val viewModel by viewModel<CreateOrderViewModel>()

    private lateinit var phone: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var totalAmount: TextInputEditText
    private lateinit var saveBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phone = view.findViewById(R.id.phone)
        description = view.findViewById(R.id.description)
        totalAmount = view.findViewById(R.id.amount)
        saveBtn = view.findViewById(R.id.save_btn)
        saveBtn.setOnClickListener {
            viewModel.createOrder(
                customerId = "customerId",
                name = "",
                phone.text.toString(),
                description.text.toString(),
                totalAmount.text.toString().toIntOrNull()?:0
            )
            viewModel.getIsSumbitted().observe(viewLifecycleOwner) {
                findNavController().popBackStack()
            }
        }
    }

}
