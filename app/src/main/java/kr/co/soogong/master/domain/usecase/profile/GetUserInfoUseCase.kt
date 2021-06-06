package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.usecase.auth.GetMasterKeyCodeUseCase
import kr.co.soogong.master.domain.user.UserDao
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetUserInfoUseCase @Inject constructor(
    private val userDao: UserDao,
    private val profileService: ProfileService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase,
) {
    // Todo.. 서버 리뉴얼 후 삭제될 클래스, 유스케이스, http req
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