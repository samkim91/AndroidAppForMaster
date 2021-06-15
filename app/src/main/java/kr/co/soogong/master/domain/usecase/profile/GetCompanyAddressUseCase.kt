package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.CompanyAddress
import javax.inject.Inject

@Reusable
class GetCompanyAddressUseCase @Inject constructor(
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase,
) {
    operator fun invoke(): Single<CompanyAddress> {
        return getRequiredInformationUseCase().map {
            it.companyAddress
        }
    }
}