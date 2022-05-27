package kr.co.soogong.master.data.source.network.common.major

import retrofit2.Retrofit
import javax.inject.Inject

class MajorService @Inject constructor(
    retrofit: Retrofit,
) {
    private val majorInterface = retrofit.create(MajorInterface::class.java)

    suspend fun getCategories() = majorInterface.getCategories()

    suspend fun getProjects(categoryId: Int) = majorInterface.getProjects(categoryId)
}