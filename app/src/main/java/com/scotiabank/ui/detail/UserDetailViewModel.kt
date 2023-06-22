package com.scotiabank.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scotiabank.data.model.RepoEntryItem

class UserDetailViewModel : ViewModel() {
    private val _mUserItem = MutableLiveData<RepoEntryItem>()
    val mUserData: LiveData<RepoEntryItem> get() = _mUserItem


     fun setupData(data : RepoEntryItem) {
        _mUserItem.value = data
    }
}