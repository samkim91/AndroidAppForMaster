package kr.co.soogong.master.presentation.ui.common.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.entity.common.major.Category
import kr.co.soogong.master.domain.usecase.common.major.GetCategoriesUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel() {
    private val _categories = MutableLiveData<List<Category>>(emptyList())
    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        requestCategories()
    }

    private fun requestCategories() {
        Timber.tag(TAG).d("getCategoryList: ")
        viewModelScope.launch {
            try {
                getCategoriesUseCase().let {
                    _categories.postValue(it)
                }
            } catch (e: Exception) {
                Timber.tag(TAG).w("getCategoryList failed: $e")
                setAction(GET_CATEGORY_FAILED)
            }
        }
    }

    companion object {
        private val TAG = CategoryViewModel::class.java.simpleName
        const val GET_CATEGORY_FAILED = "GET_CATEGORY_FAILED"
    }
}