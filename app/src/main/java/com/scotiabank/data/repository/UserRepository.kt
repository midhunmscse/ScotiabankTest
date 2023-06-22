package com.scotiabank.data.repository

import com.scotiabank.data.model.RepoEntryItem


interface UserRepository {

    suspend fun getUserSearchResponse(userId:String):List<RepoEntryItem>
}