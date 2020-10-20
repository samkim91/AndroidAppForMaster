package kr.co.soogong.master.ui.select.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.co.soogong.master.data.category.Category

class ProjectSelectViewModelFactory(
    private val category: Category
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectSelectViewModel(category) as T
    }
}
