package com.example.paging3demo.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3demo.databinding.LayoutVerticalListFooterBinding

class DefaultFooterAdapter
    : LoadStateAdapter<DefaultFooterAdapter.FooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return FooterViewHolder(
            LayoutVerticalListFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bindData(loadState)
    }

    inner class FooterViewHolder(private val binding: LayoutVerticalListFooterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(state: LoadState) {
            // 根据不同的状态，显示不同的内容，比如加载中、加载错误提示、加载完成等
            when (state) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.text = "正在加载中"
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.text = "加载异常"
                }
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                    if(state.endOfPaginationReached) {
                        binding.textView.visibility = View.VISIBLE
                        binding.textView.text = "到底了"
                    } else {
                        binding.textView.visibility = View.GONE
                        binding.textView.text = ""
                    }
                }
            }
        }
    }
}
