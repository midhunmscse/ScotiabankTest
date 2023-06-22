package com.scotiabank.data.network.source

import com.scotiabank.data.model.RepoEntryItem
import retrofit2.Response


interface UserDataSource {

    suspend fun getGithubUserDetails(userId:String): List<RepoEntryItem>

}