package com.viswakarma.jewelleryworks.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupListeners(view)
        setupObservers()
    }
    abstract fun setupView(view: View)
    abstract fun setupListeners(view: View)
    abstract fun setupObservers()
}
