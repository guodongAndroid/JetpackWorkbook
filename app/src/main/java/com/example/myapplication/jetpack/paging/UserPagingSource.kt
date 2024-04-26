package com.example.myapplication.jetpack.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.jetpack.room.User
import com.example.myapplication.jetpack.room.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by john.wick on 2024/4/25 9:41.
 */

private const val INITIAL_PAGE = 1

class UserPagingSource(
    private val userMapper: UserMapper,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: INITIAL_PAGE
        val size = params.loadSize
        val users = withContext(Dispatchers.IO) { userMapper.getUsers(page, size) }
        return try {
            LoadResult.Page(
                data = users,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (users.size < size) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}