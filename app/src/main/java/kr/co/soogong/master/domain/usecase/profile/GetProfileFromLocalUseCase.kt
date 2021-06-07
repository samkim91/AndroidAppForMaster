package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.domain.profile.ProfileDao
import kr.co.soogong.master.data.profile.Profile
import kr.co.soogong.master.domain.usecase.auth.GetMasterIdUseCase
import javax.inject.Inject

@Reusable
class GetProfileFromLocalUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val getProfileUseCase: GetProfileUseCase,
    private val getMasterIdUseCase: GetMasterIdUseCase,
) {
    suspend operator fun invoke(): Profile {
        profileDao.getItem(getMasterIdUseCase() ?: "").let { profile ->
            if (profile == null) {
                getProfileUseCase().let {
                    profileDao.insert(it)
                    return it
                }
            } else {
                return profile
            }
        }
    }
}