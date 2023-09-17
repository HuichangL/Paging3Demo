package com.example.paging3demo.paging

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.example.paging3demo.R
import com.example.paging3demo.databinding.LayoutPageStateBinding

class PageStateHost(
    private val context: Context,
    attributeSet: AttributeSet? = null,
    private val refreshAction: () -> Unit = {}
): FrameLayout(context, attributeSet) {

    private val binding by lazy {
        LayoutPageStateBinding.inflate(LayoutInflater.from(context), this, false)
    }

    private var errorView: View? = null

    fun init(viewGroup: ViewGroup) {
        (parent as? ViewGroup)?.removeView(binding.root)
        viewGroup.addView(
            binding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    fun setState(state: PageState) {
        when (state) {
            is PageState.Loading -> {
                binding.loadingView.visibility = View.VISIBLE
                binding.errorView.visibility = View.GONE
                binding.emptyView.visibility = View.GONE
            }

            is PageState.Error -> {
                binding.loadingView.visibility = View.GONE
                errorView = errorView ?: binding.errorView.inflate()
                errorView?.visibility = VISIBLE
                val retryBtn = errorView?.findViewById<Button>(R.id.btnRetry)
                retryBtn?.setOnClickListener {
                    refreshAction()
                }
                binding.emptyView.visibility = View.GONE
            }
            is PageState.Empty -> {
                binding.loadingView.visibility = View.GONE
                binding.errorView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE

            }

            else -> {
                binding.loadingView.visibility = View.GONE
                binding.errorView.visibility = View.GONE
                binding.emptyView.visibility = View.GONE
            }

        }
    }


}

sealed class PageState {
    object Loading : PageState()
    object Empty : PageState()
    class Error(val e: Throwable) : PageState()
    object NotLoading : PageState()
}
