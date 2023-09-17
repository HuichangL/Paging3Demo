package com.example.paging3demo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState


class MyPagingSource(private val apiService: ApiService) : PagingSource<Int, ListItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        try {
            // 获取请求的页数
            val pageNumber = params.key ?: 1

            val listItems = mutableListOf<ListItem>()
            // 加载数据
            apiService.getItems(pageNumber, params.loadSize)
                .onSuccess {
                    listItems.addAll(it)
                }.onFailure {
                    // 返回加载错误的 LoadResult.Error 对象
                    return LoadResult.Error(it)
                }

            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (listItems.isNotEmpty()) pageNumber + 1 else null


            // 构建 LoadResult.Page 对象并返回
            return LoadResult.Page(
                data = listItems,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            // 返回加载错误的 LoadResult.Error 对象
            return LoadResult.Error(e)
        }
    }
}
