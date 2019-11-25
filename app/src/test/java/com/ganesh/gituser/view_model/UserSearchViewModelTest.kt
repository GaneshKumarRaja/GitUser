package com.ganesh.gituser.view_model

import com.ganesh.gituser.BaseTest
import com.ganesh.gituser.data.repository.UserInfoRepoUseCase
import com.ganesh.gituser.model.Resource
import com.ganesh.gituser.model.UserModel
import kotlinx.coroutines.runBlocking

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserSearchViewModelTest : BaseTest() {
    @Mock
    lateinit var repository: UserInfoRepoUseCase

    @InjectMocks
    lateinit var viewModel: UserSearchViewModel

    private lateinit var spyViewModel: UserSearchViewModel


    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
        viewModel = UserSearchViewModel(repository)
        spyViewModel = Mockito.spy(viewModel)
    }

    @Test
    fun getUserDetails_validInput_success() {

        val responseData = createData()

        val result = Resource.Success(responseData)


        runBlocking {

            Mockito.`when`(repository.getUserDetails("testUser")).thenReturn(
                result
            )
        }

        spyViewModel.userModelLiveData.observeForever {

        }

        spyViewModel.doValidation("testUser")
        spyViewModel.getUserDetails()

        Mockito.verify(spyViewModel).parseResponse(result)

    }

    @Test
    fun doValidation_empty_error(){
        spyViewModel.doValidation("")
        assertTrue(spyViewModel.getCurrentUserName().isNullOrEmpty())
        assertTrue(!spyViewModel.errorMessage.value.isNullOrEmpty())
    }

    @Test
    fun doValidation_notEmpty_error(){
        spyViewModel.doValidation("testUser")
        assertTrue(!spyViewModel.getCurrentUserName().isNullOrEmpty())
        assertTrue(spyViewModel.errorMessage.value.isNullOrEmpty())
    }

    private fun createData(): UserModel {
        return UserModel(
            "http://www.test.com",
            "test",
            "http://www.blog.com",
            ""
        )
    }
}
