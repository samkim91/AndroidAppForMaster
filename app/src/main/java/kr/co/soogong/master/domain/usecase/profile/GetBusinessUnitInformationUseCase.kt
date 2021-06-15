package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.BusinessUnitInformation
import javax.inject.Inject

@Reusable
class GetBusinessUnitInformationUseCase @Inject constructor(
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase,
) {
    operator fun invoke(): Single<BusinessUnitInformation> {
        return getRequiredInformationUseCase().map {
            it.businessUnitInformation
        }
    }
}