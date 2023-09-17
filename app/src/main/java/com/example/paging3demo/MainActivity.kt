package com.example.paging3demo

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging3demo.databinding.ActivityMainBinding
import com.example.paging3demo.paging.MyPagingDataAdapter
import com.example.paging3demo.paging.PageState
import com.example.paging3demo.paging.setAdapterWithDefaultFooter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : BaseActivity(), BaseActivity.PagingPageState {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(MyViewModel::class.java)
    }

    private val adapter by lazy {
        MyPagingDataAdapter()
    }


    private val loadStateListener = object : Function1<CombinedLoadStates, Unit> {
        override fun invoke(combinedLoadStates: CombinedLoadStates) {
            when (val state = combinedLoadStates.refresh) {
                is LoadState.Loading -> {
                    println("refresh Loading")
                    // Show loading animation or loading indicator
                    lifecycleScope.launch {
                        pageState.emit(PageState.Loading)
                    }
                }
                is LoadState.Error -> {
                    // Show error message or handle error state
                    val errorMessage = state.error.message ?: "Unknown error"
                    // Display the error message to the user
                    lifecycleScope.launch {
                        pageState.emit(PageState.Error(Throwable(errorMessage)))
                    }
                }
                is LoadState.NotLoading -> {
                    println("refresh NotLoading")
                    // Hide loading animation or loading indicator
                    lifecycleScope.launch {
                        if (adapter.itemCount == 0) {
                            pageState.emit(PageState.Empty)
                        } else {
                            pageState.emit(PageState.NotLoading)
                        }
                    }

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initPageState(binding.root)

        initView()
        addDataObserve()
    }

    private fun addDataObserve() {
        lifecycleScope.launch {
            viewModel.myPager.flow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initView() {
       binding.apply {
           list.layoutManager = LinearLayoutManager(this@MainActivity)
           list.setAdapterWithDefaultFooter(adapter)
           adapter.addLoadStateListener(loadStateListener)

       }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.removeLoadStateListener(loadStateListener)
    }

    override val pageState: MutableStateFlow<PageState> = MutableStateFlow(PageState.Loading)
    override val refreshDataAction: () -> Unit = {
        adapter.refresh()
    }
}