package com.scotiabank.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.scotiabank.data.model.Owner
import com.scotiabank.data.model.RepoEntryItem
import com.scotiabank.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserSearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Mock
    private lateinit var repository: UserRepository

    private lateinit var viewModel: UserSearchViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = UserSearchViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchUserData should update userData LiveData with response data when successful`() = testScope.runBlockingTest {
        // Arrange
        val userId = "octocat"
        val owner = Owner("url","Midhun",1)
        val testData = RepoEntryItem("Desc",100,"Full Name",10,"Name",owner)

        val mockResponse: List<RepoEntryItem> = listOf(testData)

        // Mock the repository's getUserSearchResponse method
        whenever(repository.getUserSearchResponse(userId)).thenReturn(mockResponse)

        // Create an observer to capture the updated value of userData LiveData
        val observer = mock<Observer<List<RepoEntryItem>>>()

        try {
            // Observe the userData LiveData
            viewModel.userData.observeForever(observer)

            // Act
            viewModel.fetchUserData(userId)

            // Advance the coroutine dispatcher to execute pending coroutines
            testDispatcher.advanceUntilIdle()

            // Assert
            // Verify that the repository method is called
            verify(repository).getUserSearchResponse(userId)
            // Assert that the userData LiveData is updated with the mock response data
            assertThat(viewModel.userData.value).isEqualTo(mockResponse)
            // Verify that the observer is notified with the updated value
            verify(observer).onChanged(mockResponse)
        } finally {
            // Clean up the observer
            viewModel.userData.removeObserver(observer)
        }
    }

    @Test
    fun `fetchUserData should handle error response`() = testScope.runBlockingTest {
        // Arrange
        val userId = "hjjkjhkl"

        // Mock the repository's getUserSearchResponse method to return null
        whenever(repository.getUserSearchResponse(userId)).thenReturn(null)

        // Act
        viewModel.fetchUserData(userId)

        // Advance the coroutine dispatcher to execute pending coroutines
        testDispatcher.advanceUntilIdle()

        // Assert
        // Verify that the repository method is called
        verify(repository).getUserSearchResponse(userId)
        // Assert that the userData LiveData is null for error response
        assertThat(viewModel.errorData.value == "Something went wrong")
    }
}
