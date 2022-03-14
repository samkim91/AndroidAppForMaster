package kr.co.soogong.master.domain.entity.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.AttachmentDto

@Parcelize
data class Attachment(
    val id: Int,
    val partOf: String,
    val referenceId: Int,
    val description: String,
    val s3Name: String,
    val fileName: String,
    val url: String,
) : Parcelable {
    companion object {
        fun fromDtos(attachmentDtos: List<AttachmentDto>?): List<Attachment>? =
            attachmentDtos?.map { attachmentDto ->
                fromDto(attachmentDto)!!
            }

        fun fromDto(attachmentDto: AttachmentDto?): Attachment? {
            return attachmentDto?.let {
                Attachment(
                    id = attachmentDto.id!!,
                    partOf = attachmentDto.partOf!!,
                    referenceId = attachmentDto.referenceId!!,
                    description = attachmentDto.description!!,
                    s3Name = attachmentDto.s3Name!!,
                    fileName = attachmentDto.fileName!!,
                    url = attachmentDto.url!!,
                )
            }
        }
    }
}