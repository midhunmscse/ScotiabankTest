package com.scotiabank.data.network.source

import com.scotiabank.data.network.ApiService
import com.scotiabank.data.model.RepoEntryItem
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    UserDataSource {
    override suspend fun getGithubUserDetails(userId:String): List<RepoEntryItem> {
        return apiService.requestGetUserDetailsAsync(userId).await()
    }

}
