package com.ganesh.gituser.data.repository

import com.ganesh.gituser.model.UserModel
import com.ganesh.gituser.model.Resource

interface UserInfoRepoUseCase {
    suspend fun getUserDetails(userName:String): Resource<UserModel>
}