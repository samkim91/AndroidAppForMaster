package kr.co.soogong.master.network.major

import retrofit2.Retrofit
import javax.inject.Inject

class MajorService @Inject constructor(
    retrofit: Retrofit,
) {
    private val majorInterface = retrofit.create(MajorInterface::class.java)

    fun getCategories() = majorInterface.getCategories()

    fun getProjects(categoryId: Int) = majorInterface.getProjects(categoryId)
}