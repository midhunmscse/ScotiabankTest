package com.scotiabank.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scotiabank.data.model.RepoEntryItem
import com.scotiabank.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _userData = MutableLiveData<List<RepoEntryItem>>()
    val userData: LiveData<List<RepoEntryItem>> get() = _userData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String> get() = _errorData

    fun fetchUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Call your API with the userId parameter
                val response = repository.getUserSearchResponse(userId)
                if (response!=null) {

                    _userData.postValue(response)
                }else{
                    _errorData.postValue("Something went wrong")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.e("","")
                _errorData.postValue("Something went wrong")
            }
        }
    }
}