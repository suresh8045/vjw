package com.viswakarma.jewelleryworks.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter(diffUtil: DiffUtil.ItemCallback<Any>) :
    ListAdapter<Any, GenericAdapter.GenericViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return getViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.onBind(position, getItem(position))
    }

    protected abstract fun getLayoutId(position: Int, obj: Any): Int
    abstract fun getViewHolder(view: View, viewType: Int):GenericViewHolder
    abstract class GenericViewHolder(view: View) : RecyclerView.ViewHolder(view){
        abstract fun onBind(position: Int, item: Any)
    }
}