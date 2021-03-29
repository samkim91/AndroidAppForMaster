package kr.co.soogong.master.network

import kr.co.soogong.master.data.user.User
import retrofit2.Retrofit
import javax.inject.Inject

class UserService @Inject constructor(
    retrofit: Retrofit
) {
    private val userInterface = retrofit.create(UserInterface::class.java)

    suspend fun getUserProfile(keycode: String?): User {
        return User.fromJson(userInterface.getUserProfile(keycode))
    }
}