package kr.co.soogong.master.domain.usecase.profile

import android.net.Uri
import androidx.core.net.toUri
import dagger.Reusable
import io.reactivex.Single
import javax.inject.Inject

@Reusable
class GetThumbnailsUseCase @Inject constructor(
    private val getRequiredInformationUseCase: GetRequiredInformationUseCase,
) {
    operator fun invoke(): Single<List<Uri>> {
        return getRequiredInformationUseCase().map {
            it.representativeImages?.map { attachment ->
                attachment.url?.toUri()!!
            }
        }
    }
}