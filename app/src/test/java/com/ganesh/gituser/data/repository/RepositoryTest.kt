package com.ganesh.gituser.data.repository

import com.ganesh.gituser.BaseTest
import com.ganesh.gituser.data.remote.HttpApi
import com.ganesh.gituser.model.Resource
import com.ganesh.gituser.model.UserModel
import com.ganesh.gituser.util.InternetConnection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`


class RepositoryTest : BaseTest() {


    @Mock
    lateinit var mockAPI: HttpApi


    @Mock
    lateinit var internetConnection: InternetConnection


    @InjectMocks
    lateinit var rateService: UserInfoRepo

    @Before
    fun initAll() {
        MockitoAnnotations.initMocks(this)
        rateService = UserInfoRepo(mockAPI, internetConnection)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTicketDetails_valid_onlineData() {

        val list = createData()

        runBlocking {

            internetConnection.isAvailable()
            `when`(internetConnection.isAvailable()).thenReturn(true)

            `when`(
                mockAPI.getUserDetailsAsync("testUser")
            ).thenReturn(async {
                list
            })

            val result = rateService.getUserDetails("testUser")

            Assert.assertEquals(true, result is Resource.Success)

            Assert.assertEquals(false, result is Resource.Error)


        }


    }


    @ExperimentalCoroutinesApi
    @Test
    fun getTicketDetails_error_online() {

        val ex = Throwable("error")

        runBlocking {
            internetConnection.isAvailable()
            `when`(internetConnection.isAvailable()).thenReturn(true)

            `when`(
                mockAPI.getUserDetailsAsync("testUser")
            ).then {

                (async {
                    ex
                })
            }

            val result = rateService.getUserDetails("testUser")

            Assert.assertEquals(false, result is Resource.Success)

            Assert.assertEquals(true, result is Resource.Error)
        }
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
