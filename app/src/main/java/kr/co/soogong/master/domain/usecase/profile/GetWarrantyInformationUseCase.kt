package kr.co.soogong.master.domain.usecase.profile

import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.model.profile.WarrantyInformation
import javax.inject.Inject

@Reusable
class GetWarrantyInformationUseCase @Inject constructor(
    private val requiredInformationUseCase: GetRequiredInformationUseCase,
) {
    operator fun invoke(): Single<WarrantyInformation> {
        return requiredInformationUseCase().map {
            it.warrantyInformation
        }
    }
}