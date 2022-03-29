package kr.co.soogong.master.domain.entity.requirement.repair

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto
import kr.co.soogong.master.domain.entity.DtoConverter
import kr.co.soogong.master.domain.entity.requirement.review.Review
import java.util.*

@Parcelize
data class Repair(
    val id: Int,
    val estimationId: Int,
    val actualDate: Date,
    val actualPrice: Int,
    val includingVat: Boolean,
    val warrantyDueDate: Date?,
    val requestReviewYn: Boolean,
    val images: List<String>?,
    val review: Review?,
    val createdAt: Date,
    val updatedAt: Date,
) : Parcelable {

    companion object: DtoConverter<RepairDto, Repair> {
        override fun fromDto(dto: RepairDto): Repair {
            return Repair(
                id = dto.id!!,
                estimationId = dto.estimationId!!,
                actualDate = dto.actualDate!!,
                actualPrice = dto.actualPrice ?: 0,
                includingVat = dto.includingVat ?: false,
                warrantyDueDate = dto.warrantyDueDate,
                requestReviewYn = dto.requestReviewYn ?: false,
                images = dto.images,
                review = if(dto.review != null) Review.fromDto(dto.review) else null,
                createdAt = dto.createdAt!!,
                updatedAt = dto.updatedAt!!,
            )
        }
    }
}