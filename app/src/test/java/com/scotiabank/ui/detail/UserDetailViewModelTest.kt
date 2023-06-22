package com.scotiabank.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import com.scotiabank.data.model.Owner
import com.scotiabank.data.model.RepoEntryItem
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UserDetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<RepoEntryItem>

    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = UserDetailViewModel()
        viewModel.mUserData.observeForever(observer)
    }

    @Test
    fun `setupData should update mUserData with the provided data`() {
        // Arrange
        val owner = Owner("url","Midhun",1)
        val testData = RepoEntryItem("Desc",100,"Full Name",10,"Name",owner)

        // Act
        viewModel.setupData(testData)

        // Assert
        verify(observer).onChanged(testData)
        assertThat(viewModel.mUserData.value).isEqualTo(testData)
    }
}
