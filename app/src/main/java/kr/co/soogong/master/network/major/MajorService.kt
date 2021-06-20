package kr.co.soogong.master.network.major

import io.reactivex.Single
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.data.model.major.Project
import retrofit2.Retrofit
import javax.inject.Inject

class MajorService @Inject constructor(
    retrofit: Retrofit
) {
    private val categoryInterface = retrofit.create(MajorInterface::class.java)

    fun getCategoryList(): Single<List<Category>> {
        return categoryInterface.getCategoryList()
//            .map {
//            val items: MutableList<Category> = ArrayList()
//            for (item in it) {
//                items.add(Category.fromJson(item.asJsonObject))
//            }
//            return@map items
//        }
    }

    fun getProjectList(categoryId: Int): Single<List<Project>> {
        return categoryInterface.getProjectList(categoryId)
//            .map {
//            val items: MutableList<Project> = ArrayList()
//            for (item in it) {
//                items.add(Project.fromJson(item.asJsonObject))
//            }
//            return@map items
//        }
    }
}