package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.viswakarma.jewelleryworks.databinding.AddTransactionFragmentBinding
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.add_transaction_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTransactionFragment : BaseFragment() {

    private val viewModel: AddTransactionViewModel by viewModel()
    private val args: AddTransactionFragmentArgs by navArgs()
    private lateinit var transactionType: AutoCompleteTextView
    private lateinit var purity: AutoCompleteTextView
    private lateinit var amountInputLayout: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddTransactionFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun setupView(view: View) {
        viewModel.orderId = args.orderId
        transactionType = view.transactionType
        purity = view.purity
        amountInputLayout = view.amountInputLayout
    }

    override fun setupListeners(view: View) {

    }

    override fun setupObservers() {
        viewModel.transactionTypes.observe(viewLifecycleOwner){ list ->
            transactionType.run {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    viewModel.setSelectedTransactionType(list[position])
//                    doItemTypeValidation(this@AddToCatalogueFragment.itemTypeLayout)
                }
            }
        }
        viewModel.purity.observe(viewLifecycleOwner){ listOfValues ->
            purity.run {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listOfValues)
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    viewModel.setSelectedPurityPercentage(listOfValues[position])
                }
            }
        }
        viewModel.amountInputError.observe(viewLifecycleOwner){
            amountInputLayout.error = it
        }
        viewModel.isTransactionAdded.observe(viewLifecycleOwner){
            if(it) {
                findNavController().navigateUp()
            }
        }
    }
}