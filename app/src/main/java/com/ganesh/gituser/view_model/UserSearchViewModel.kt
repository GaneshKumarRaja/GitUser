package com.ganesh.gituser.view_model


import androidx.lifecycle.MutableLiveData
import com.ganesh.gituser.data.repository.UserInfoRepoUseCase
import com.ganesh.gituser.model.UserModel
import com.ganesh.gituser.model.Resource
import debug.OpenForTesting
import kotlinx.coroutines.*
import javax.inject.Inject


@OpenForTesting
class UserSearchViewModel
@Inject constructor(private val repository: UserInfoRepoUseCase) : BaseViewModel() {

    // currently looking for user
    private var currentUserName: String? = null

    var userModelLiveData: MutableLiveData<UserModel> = MutableLiveData()


    /**
     * get the user details from repo
     */
    fun getUserDetails() {

        canShowLoading.value = true

        launch {

            val result = withContext(Dispatchers.IO) {
                repository.getUserDetails(currentUserName!!)
            }

            parseResponse(result)

        }

    }

    /**
     * parsing the response data of user details got from repo
     */
    fun parseResponse(result: Resource<UserModel>) {

        when (result) {

            is Resource.Success -> {
                // ticketDetailsLiveData.value=result.data
                if (result.data.message.isNullOrEmpty()) {
                    userModelLiveData.value = result.data
                } else {
                    // clearResult()
                    errorMessage.value = result.data.message
                }
            }

            is Resource.Error -> {
                // clearResult()
                errorMessage.value = result.exception.message
            }

        }

        canShowLoading.value = true
    }


    /**
     * validating user name of github
     */
    fun doValidation(userName: String): Boolean {

        return if (userName.isNotEmpty()) {
            currentUserName = userName
            true
        } else {
            errorMessage.value = "Enter User Name"
            false
        }

    }

    fun clearResult() {
        userModelLiveData.value = null
        errorMessage.value = null
        currentUserName = null
    }

    fun getCurrentUserName(): String? {
        return currentUserName
    }


}



