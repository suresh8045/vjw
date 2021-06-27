package com.viswakarma.jewelleryworks.view.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.viswakarma.jewelleryworks.OrdersFragment
import com.viswakarma.jewelleryworks.OrdersFragmentDirections
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Catalogue
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Order
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Transaction
import com.viswakarma.jewelleryworks.model.util.getDateTime
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
            is Transaction -> {
                TransactionsViewHolder.resource
            }
            is Order -> {
                OrderViewHolder.resource
            }
            else -> 0
        }
    }

    override fun getViewHolder(view: View, viewType: Int): GenericViewHolder {
        return when(viewType){
            CatalogueViewHolder.resource -> CatalogueViewHolder(view)
            TopCustomerViewHolder.resource -> TopCustomerViewHolder(view)
            TransactionsViewHolder.resource -> TransactionsViewHolder(view)
            OrderViewHolder.resource -> OrderViewHolder(view)
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
        private var weight: TextView = view.findViewById(R.id.weight)
        private var metal: TextView = view.findViewById(R.id.metal)
        companion object{
            const val resource = R.layout.recycler_catalogue_itemview
        }
        override fun onBind(position: Int, item: Any) {
            (item as? Catalogue)?.let{
                name.text = it.name
                category.text = it.category
                type.text = it.type
                modelNo.text = it.modelNo
                weight.text = it.getWeightFormat()
                metal.text = it.metal
                val decodedString = Base64.decode(it.image, Base64.NO_WRAP)
                catalogueImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0,decodedString.size))
            }
        }
    }

    class TopCustomerViewHolder(val view: View) : GenericViewHolder(view){
        private var customerName: TextView = view.findViewById(R.id.customerName)
        private var phoneNo: TextView = view.findViewById(R.id.phoneNo)
        private var createOrderButton: Button = view.findViewById(R.id.createOrderButton)
        private var phoneCallButton: Button = view.findViewById(R.id.phoneCall)
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
                phoneCallButton.setOnClickListener {
                    view.findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToOrderDetailsFragment())
                }
            }
        }
    }

    class TransactionsViewHolder(val view: View) : GenericViewHolder(view){
        private var transactionType: TextView = view.findViewById(R.id.transactionType)
        private var description: TextView = view.findViewById(R.id.description)
        private var date: TextView = view.findViewById(R.id.date)
        private var weight: TextView = view.findViewById(R.id.weight)
        private var purity: TextView = view.findViewById(R.id.purity)
        private var amount: TextView = view.findViewById(R.id.amount)
        companion object{
            const val resource = R.layout.recycler_transaction_itemview
        }
        override fun onBind(position: Int, item: Any) {
            (item as? Transaction)?.let{ transaction ->
                transactionType.text = transaction.type
                description.text = transaction.description
                date.text = transaction.dateTime.getDateTime()
                weight.text = transaction.getWeightFormat()
                purity.text = transaction.getPurityFormat()
                amount.text = transaction.getAmountFormat()

            }
        }
    }

    class OrderViewHolder(val view: View) : GenericViewHolder(view){
        private var model: TextView = view.findViewById(R.id.model)
        private var description: TextView = view.findViewById(R.id.description)
        private var date: TextView = view.findViewById(R.id.date)
        private var weight: TextView = view.findViewById(R.id.weight)
        private var metal: TextView = view.findViewById(R.id.metal)
        companion object{
            const val resource = R.layout.recycler_order_itemview
        }
        override fun onBind(position: Int, item: Any) {
            (item as? Order)?.let{ order ->
                model.text = order.modelNo
                description.text = order.description
                date.text = order.dateTime.getDateTime()
                weight.text = order.getWeightFormat()
                metal.text = order.metal
            }
            view.setOnClickListener {
                it.findNavController().navigate(OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment())
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