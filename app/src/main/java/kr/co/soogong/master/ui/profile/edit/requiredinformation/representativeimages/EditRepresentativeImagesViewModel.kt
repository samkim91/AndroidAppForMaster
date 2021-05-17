package kr.co.soogong.master.ui.profile.edit.requiredinformation.representativeimages

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.soogong.master.domain.usecase.profile.GetRepresentativeImagesUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.utils.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditRepresentativeImagesViewModel @Inject constructor(
//    private val saveOtherFlexibleOptionsUseCase: SaveOtherFlexibleOptionsUseCase,
    private val getRepresentativeImagesUseCase: GetRepresentativeImagesUseCase,
) : BaseViewModel() {
    val representativeImages = ListLiveData<Uri>()
    
    fun getRepresentativeImg() {
        Timber.tag(TAG).d("getRepresentativeImg: ")
        viewModelScope.launch {
            representativeImages.addAll(getRepresentativeImagesUseCase())
        }
    }

    fun saveRepresentativeImg() {
        Timber.tag(TAG).d("saveRepresentativeImg: ")


    }

    companion object {
        private const val TAG = "EditRepresentativeImagesViewModel"

    }
}