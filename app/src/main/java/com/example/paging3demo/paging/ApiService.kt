package com.example.paging3demo.paging

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

interface ApiService {
    suspend fun getItems(
        pageNumber: Int,
        pageSize: Int
    ): Result<List<ListItem>>
}

class MockApiService : ApiService {
    override suspend fun getItems(
        pageNumber: Int,
        pageSize: Int
    ): Result<List<ListItem>> {
        return try {
            val result = withContext(Dispatchers.IO) {
                delay(3000) // 模拟延时请求

                // 构造示例数据
                val startIndex = (pageNumber - 1) * pageSize + 1
                val endIndex = startIndex + pageSize
                val items = (startIndex until endIndex).map { index ->
                    ListItem(index, "Item $index")
                }

                items
            }

            Result.success(result)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


