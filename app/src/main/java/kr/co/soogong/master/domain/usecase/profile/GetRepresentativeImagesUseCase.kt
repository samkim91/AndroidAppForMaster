package kr.co.soogong.master.domain.usecase.profile

import android.net.Uri
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class GetRepresentativeImagesUseCase @Inject constructor(
    private val getProfileFromLocalUseCase: GetProfileFromLocalUseCase,
) {
    operator fun invoke(): Single<List<Uri>> {
//        if (BuildConfig.DEBUG) {
//            return RequiredInformation.TEST_REQUIRED_INFORMATION.representativeImages.map { imagePath ->
//                Uri.parse(imagePath.path)
//            }
//        }

        return getProfileFromLocalUseCase().map { profile ->
            profile.requiredInformation?.representativeImages?.map { imagePath ->
                Uri.parse(imagePath.path)
            }
        }
    }
}