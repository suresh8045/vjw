package com.viswakarma.jewelleryworks.view.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.view.fragment.CreateOrderFragment
import com.viswakarma.jewelleryworks.view.fragment.HomeFragmentDirections

class DataAdapter(context: Context) : GenericAdapter(DataItemDiffCallback()) {

    override fun getLayoutId(position: Int, obj: Any): Int {
        return when(obj){
            is Catalogue -> {
                CatalogueViewHolder.resource
            }
            is Customer -> {
                TopCustomerViewHolder.resource
            }
            else -> 0
        }
    }

    override fun getViewHolder(view: View, viewType: Int): GenericViewHolder {
        return when(viewType){
            CatalogueViewHolder.resource -> CatalogueViewHolder(view)
            TopCustomerViewHolder.resource -> TopCustomerViewHolder(view)
            else ->{
                CatalogueViewHolder(view)
            }
        }
    }

    class CatalogueViewHolder(view: View) : GenericViewHolder(view){
        private var catalogueImage: ImageView = view.findViewById(R.id.catalogueImage)
        private var name: TextView = view.findViewById(R.id.name)
        private var category: TextView = view.findViewById(R.id.category)
        private var type: TextView = view.findViewById(R.id.type)
        private var modelNo: TextView = view.findViewById(R.id.modelno)
        companion object{
            const val resource = R.layout.recycler_catalogue_itemview
        }
        override fun onBind(position: Int, item: Any) {
            (item as? Catalogue)?.let{
                name.text = it.name
                category.text = it.category
                type.text = it.type
                modelNo.text = it.modelNo
                val decodedString = Base64.decode(it.image, Base64.NO_WRAP)
                catalogueImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0,decodedString.size))

            }
        }
    }

    class TopCustomerViewHolder(val view: View) : GenericViewHolder(view){
        private var customerName: TextView = view.findViewById(R.id.customerName)
        private var phoneNo: TextView = view.findViewById(R.id.phoneNo)
        private var createOrderButton: Button = view.findViewById(R.id.createOrderButton)
        companion object{
            const val resource = R.layout.recycler_customer_square_itemview
        }
        override fun onBind(position: Int, item: Any) {
            (item as? Customer)?.let{ customer ->
                customerName.text = customer.name
                phoneNo.text = customer.phone
                createOrderButton.setOnClickListener {
                    view.findNavController().navigate(
                        HomeFragmentDirections.actionNavigationHomeToNavigationCreateOrder()
                            .setCustomerId(customer.id)
                    )
                }
            }
        }
    }

    class DataItemDiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when (oldItem) {
                is Catalogue -> {
                    oldItem.areItemsTheSame(newItem as Catalogue)
                }
                is Customer -> {
                    oldItem.areItemsTheSame(newItem as Customer)
                }
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when(oldItem){
                is Catalogue -> {
                    oldItem.areContentsTheSame(newItem as Catalogue)
                }
                is Customer -> {
                    oldItem.areContentsTheSame(newItem as Customer)
                }
                else -> false
            }
        }
    }
}