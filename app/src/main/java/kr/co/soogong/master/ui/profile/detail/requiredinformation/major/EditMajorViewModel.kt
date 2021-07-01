package kr.co.soogong.master.ui.profile.detail.requiredinformation.major

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MajorDto
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.utility.ListLiveData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditMajorViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile>()

    val majors = ListLiveData<Major>()

    fun requestMajor() {
        Timber.tag(TAG).d("requestMajor: ")

        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { profile ->
                    Timber.tag(TAG).d("requestMajor successfully: $profile")
                    _profile.value = profile
                    profile.requiredInformation?.majors?.let {
                        majors.addAll(it)
                    }
                },
                onError = {
                    Timber.tag(TAG).d("requestMajor failed: $it")
                    setAction(GET_MAJOR_FAILED)
                }
            ).addToDisposable()
    }

    fun saveMajor() {
        Timber.tag(TAG).d("saveMajor: ")
        majors.value?.let {
            saveMasterUseCase(
                MasterDto(
                    id = _profile.value?.id,
                    uid = _profile.value?.uid,
                    majors = MajorDto.fromMajorList(majors.value),
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { setAction(SAVE_MAJOR_SUCCESSFULLY) },
                    onError = { setAction(SAVE_MAJOR_FAILED) }
                ).addToDisposable()
        }
    }

    companion object {
        private const val TAG = "EditMajorViewModel"
        const val SAVE_MAJOR_SUCCESSFULLY = "SAVE_MAJOR_SUCCESSFULLY"
        const val SAVE_MAJOR_FAILED = "SAVE_MAJOR_FAILED"
        const val GET_MAJOR_FAILED = "GET_MAJOR_FAILED"
    }
}