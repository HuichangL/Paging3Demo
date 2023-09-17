package com.example.paging3demo

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.paging3demo.paging.PageState
import com.example.paging3demo.paging.PageStateHost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseActivity: AppCompatActivity() {

    private var pageStateHost: PageStateHost? = null

    fun initPageState(viewGroup: ViewGroup) {
        if (this is PagingPageState) {
            pageStateHost = PageStateHost(viewGroup.context, refreshAction = refreshDataAction).apply {
                init(viewGroup)
                lifecycleScope.launch {
                    pageState.collectLatest {
                        setState(it)
                    }
                }
            }
        }
    }

    interface PagingPageState {
        val pageState: MutableStateFlow<PageState>
        val refreshDataAction: () -> Unit
    }
}