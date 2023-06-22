package com.scotiabank.data.repository

import com.scotiabank.data.network.source.UserDataSource
import com.scotiabank.data.model.RepoEntryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUserSearchResponse(userId:String): List<RepoEntryItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getGithubUserDetails(userId)
        }
    }

}