package kr.co.soogong.master.network

import io.reactivex.Single
import kr.co.soogong.master.data.user.User
import retrofit2.Retrofit
import javax.inject.Inject

class UserService @Inject constructor(
    retrofit: Retrofit
) {
    private val userInterface = retrofit.create(UserInterface::class.java)

    fun getUserProfile(keycode: String?): Single<User> {
        return userInterface.getUserProfile(keycode).map {
            User.fromJson(it)
        }
    }
}