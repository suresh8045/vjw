package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTransactionFragment : BaseFragment() {

    private val viewModel: AddTransactionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.add_transaction_fragment, container, false)
    }

    override fun setupView(view: View) {

    }

    override fun setupListeners(view: View) {
    }

    override fun setupObservers() {
    }


}