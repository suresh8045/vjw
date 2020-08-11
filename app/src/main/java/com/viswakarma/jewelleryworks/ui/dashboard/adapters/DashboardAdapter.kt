package com.viswakarma.jewelleryworks.ui.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.viswakarma.jewelleryworks.R
import com.viswakarma.jewelleryworks.roomdb.models.Orders
import com.viswakarma.jewelleryworks.util.Common
import timber.log.Timber

class DashboardAdapter(var context: Context, var onDashboardItemsInteractionListener: OnDashboardItemsInteractionListener, var itemOnClick: (Any) -> Unit) :
    ListAdapter<Any, RecyclerView.ViewHolder>(DashboardItemDiffCallback()) {


    companion object{
        const val DASHBOARD_ORDER = 1

    }

    //the class is hodling the list view
    class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name:TextView = itemView.findViewById(R.id.name)
        var date:TextView = itemView.findViewById(R.id.date)
        var due:TextView = itemView.findViewById(R.id.due)
        var description:TextView = itemView.findViewById(R.id.description)

        fun onBind(item: Orders) {
            name.text = item.name
            due.text = item.totalAmount.toString()
            description.text = item.description
            date.text = Common.getDateString(item.date)
        }
    }


    class DashboardItemDiffCallback: DiffUtil.ItemCallback<Any>(){
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if(oldItem is Orders && newItem is Orders){
                oldItem == newItem
            }else {
                false
            }
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if(oldItem is Orders && newItem is Orders){
                return (oldItem as Orders) == (newItem as Orders)
            }else {
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.i("onCreateViewHolder $viewType")
        val view : View
        return when(viewType){
            DASHBOARD_ORDER ->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_order_list_item, parent, false)
                OrdersViewHolder(view)
            }
            else->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_order_list_item, parent, false)
                OrdersViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is Orders->{
                DASHBOARD_ORDER
            }
            else->{
                super.getItemViewType(position)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Timber.i(" list position $position")
        (holder as? OrdersViewHolder)?.onBind(getItem(position) as Orders)
    }

    interface OnDashboardItemsInteractionListener{
    }

}
