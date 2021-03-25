package kr.co.soogong.master.network

import io.reactivex.Single
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.data.category.Project
import retrofit2.Retrofit
import javax.inject.Inject

class CategoryService @Inject constructor(
    retrofit: Retrofit
) {
    private val categoryInterface = retrofit.create(CategoryInterface::class.java)

    fun getCategoryList(): Single<List<Category>> {
        return categoryInterface.getCategoryList().map {
            val items: MutableList<Category> = ArrayList()
            for (item in it) {
                items.add(Category.fromJson(item))
            }
            return@map items
        }
    }

    fun getProjectList(category: Category): Single<List<Project>> {
        return categoryInterface.getProjectList(category.id).map {
            val items: MutableList<Project> = ArrayList()
            for (item in it) {
                items.add(Project.fromJson(item))
            }
            return@map items
        }
    }
}