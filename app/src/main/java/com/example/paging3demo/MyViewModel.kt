package com.example.paging3demo

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.paging3demo.paging.MockApiService
import com.example.paging3demo.paging.MyPagingSource

class MyViewModel: ViewModel() {

    private val apiService = MockApiService()

    val myPager = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 2, initialLoadSize = 20),
        initialKey = 1,
        pagingSourceFactory = {
            MyPagingSource(apiService)
        }
    )
}