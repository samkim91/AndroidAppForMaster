package kr.co.soogong.master.domain.usecase

import dagger.Reusable
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.user.UserDao
import kr.co.soogong.master.network.UserService
import javax.inject.Inject

@Reusable
class GetUserInfoUseCase @Inject constructor(
    private val userDao: UserDao,
    private val userService: UserService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
) {
    suspend operator fun invoke(): User {
        val user = userDao.getItem(getMasterKeyCodeUseCase() ?: "")
        if (user == null) {
            val userData = userService.getUserProfile(getMasterKeyCodeUseCase())
            userDao.insert(userData)
            return userData
        }
        return user
    }
}