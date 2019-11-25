package com.ganesh.gituser.data.repository


import com.ganesh.gituser.model.UserModel
import com.ganesh.gituser.data.remote.HttpApi
import com.ganesh.gituser.model.Resource
import com.ganesh.gituser.util.InternetInterface
import com.ganesh.gituser.util.NoConnectivityException
import debug.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class UserInfoRepo
@Inject constructor(
    private val httpApi: HttpApi,
    private var internetConnection: InternetInterface
) : UserInfoRepoUseCase {

    override suspend fun getUserDetails(userName: String): Resource<UserModel> {
        return try {
            // find internet connection
            if (internetConnection.isAvailable()) {
                //get user details from the server
                val result = httpApi.getUserDetailsAsync(userName).await()
                Resource.Success(result)

            } else {
                // no internet
                Resource.Error(NoConnectivityException())
            }

        } catch (ex: Exception) {
            // error message
            Resource.Error(ex)
        }
    }
}


