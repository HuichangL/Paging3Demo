package com.example.paging3demo.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3demo.databinding.ItemListBinding
/**
 * Instantiates a layout XML file into its corresponding {@link android.view.View}
 * objects. It is never used directly. Instead, use
 * {@link android.app.Activity#getLayoutInflater()} or
 * {@link Context#getSystemService} to retrieve a standard LayoutInflater instance
 * that is already hooked up to the current context and correctly configured
 * for the device you are running on.
 *
 * */
class MyPagingDataAdapter: PagingDataAdapter<ListItem, MyPagingDataAdapter.ListItemViewHolder>(ItemComparator) {

    // 自定义 ItemComparator，用于比较数据项
    object ItemComparator : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class ListItemViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.index.text = item?.id.toString()
        holder.binding.tvName.text = item?.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context) ,parent, false))
    }
}