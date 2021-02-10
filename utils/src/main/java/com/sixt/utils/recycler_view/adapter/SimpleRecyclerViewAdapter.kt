package com.sixt.utils.recycler_view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

typealias ViewHolderBuilder<T> = (view: View) -> SimpleViewHolder<T>

class SimpleRecyclerViewAdapter<T>(
    private var itemResId: Int,
    private var viewHolderBuilder: ViewHolderBuilder<T>
) : RecyclerView.Adapter<SimpleViewHolder<T>>() {


    private var dataList: MutableList<T> = mutableListOf()

    fun addItem(data: T) {
        dataList.add(data)
    }

    fun addItem(data: T, index: Int) {
        dataList.add(index, data)
    }

    fun setItem(data: T, index: Int) {
        dataList[index] = data
    }

    fun removeItem(index: Int) {
        dataList.removeAt(index)
    }

    fun getItem(i: Int): T? {
        return dataList[i]
    }

    fun addAll(dataList: List<T>) {
        this.dataList.addAll(dataList)
    }

    fun setData(dataList: List<T>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
    }

    fun clear() {
        dataList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder<T> {
        val itemView = LayoutInflater.from(parent.context).inflate(itemResId, parent, false)
        return viewHolderBuilder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<T>, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
