package com.example.myapplication.jetpack.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.jetpack.room.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by john.wick on 2024/4/25 10:07.
 */

private const val ITEMS_PRE_PAGE = 5

class UserViewModel(repository: UserRepository) : ViewModel() {

    val users: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = ITEMS_PRE_PAGE,
            initialLoadSize = ITEMS_PRE_PAGE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { repository.userPagingSource(false) }
    ).flow.cachedIn(viewModelScope)

}