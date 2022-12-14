package kr.co.soogong.master.domain.entity.common.major

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.common.major.CategoryDto

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val nameEn: String?,
) : Parcelable {
    companion object {
        fun fromCategoryDtos(categoryDtos: List<CategoryDto>?) =
            categoryDtos?.map { categoryDto ->
                Category(
                    id = categoryDto.id,
                    name = categoryDto.name,
                    nameEn = categoryDto.nameEn
                )
            }
    }
}