package com.viswakarma.jewelleryworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.viswakarma.jewelleryworks.view.fragment.BaseFragment

class OrderDetailsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_scrolling2, container, false)
    }

    override fun setupView(view: View) {

    }

    override fun setupListeners(view: View) {
    }

    override fun setupObservers() {
    }

}