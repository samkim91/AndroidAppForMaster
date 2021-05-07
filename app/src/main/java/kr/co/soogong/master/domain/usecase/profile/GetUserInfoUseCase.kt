package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.user.UserDao
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetUserInfoUseCase @Inject constructor(
    private val userDao: UserDao,
    private val profileService: ProfileService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
) {
    suspend operator fun invoke(): User {
        val user = userDao.getItem(getMasterKeyCodeUseCase() ?: "")
        if (user == null) {
            val userData = profileService.getUserProfile(getMasterKeyCodeUseCase())
            userDao.insert(userData)
            return userData
        }
        return user
    }
}