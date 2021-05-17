package kr.co.soogong.master.domain.usecase.profile

import android.net.Uri
import dagger.Reusable
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.data.profile.RequiredInformation
import javax.inject.Inject

@Reusable
class GetRepresentativeImagesUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    suspend operator fun invoke(): List<Uri> {
        if (BuildConfig.DEBUG) {

            return RequiredInformation.TEST_REQUIRED_INFORMATION.representativeImages.map { imagePath ->
                Uri.parse(imagePath.path)
            }
        }

        getProfileFromLocalUseCase().let { profile ->
            return profile.requiredInformation?.representativeImages?.map { imagePath ->
                Uri.parse(
                    imagePath.path
                )
            }
                ?: RequiredInformation.NULL_REQUIRED_INFORMATION.representativeImages.map { imagePath ->
                    Uri.parse(
                        imagePath.path
                    )
                }
        }
    }
}