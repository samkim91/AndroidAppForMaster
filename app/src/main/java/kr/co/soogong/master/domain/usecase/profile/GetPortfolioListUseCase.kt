package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import kr.co.soogong.master.data.profile.Portfolio
import kr.co.soogong.master.domain.usecase.GetMasterKeyCodeUseCase
import kr.co.soogong.master.network.ProfileService
import javax.inject.Inject

@Reusable
class GetPortfolioListUseCase @Inject constructor(
    private val profileService: ProfileService,
    private val getMasterKeyCodeUseCase: GetMasterKeyCodeUseCase
) {
    suspend operator fun invoke(): List<Portfolio> {
        return profileService.getPortfolioList(getMasterKeyCodeUseCase()!!)
    }
}