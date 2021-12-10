package kr.co.soogong.master.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.data.model.profile.ApprovedCodeTable
import kr.co.soogong.master.data.model.profile.Profile
import kr.co.soogong.master.data.model.profile.RequestApproveCodeTable
import kr.co.soogong.master.domain.usecase.profile.GetMasterUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.ui.base.BaseViewModel
import kr.co.soogong.master.ui.profile.detail.CareerConverter
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMasterUseCase: GetMasterUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _profile = MutableLiveData<Profile?>()
    val profile: LiveData<Profile?>
        get() = _profile

    val profileImage = MutableLiveData(Uri.EMPTY)

    fun requestProfile() {
        Timber.tag(TAG).d("requestProfile: ")
        getMasterUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("getMasterSuccessfully: ")
                    _profile.value = Profile.fromMasterDto(it)
                },
                onError = {
                    Timber.tag(TAG).d("getMasterFailed: $it")
                    setAction(REQUEST_FAILED)
                }
            )
            .addToDisposable()
    }

    fun saveMasterProfileImage() {
        Timber.tag(TAG).d("saveMasterProfileImage: ")
        saveMasterUseCase(
            masterDto = MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                approvedStatus = if (_profile.value?.approvedStatus == ApprovedCodeTable.code) RequestApproveCodeTable.code else null,
            ),
            profileImageUri = profileImage.value,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { setAction(DISMISS_LOADING) }
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveMasterProfile successfully: ")
                },
                onError = {
                    Timber.tag(TAG).d("saveMasterProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveCareerPeriod(careerPeriod: Int) {
        Timber.tag(TAG).d("saveCareerPeriod: ")

        saveMasterUseCase(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                openDate = CareerConverter.toOpenDate(careerPeriod),
                approvedStatus = if (profile.value?.approvedStatus == ApprovedCodeTable.code) RequestApproveCodeTable.code else null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { setAction(DISMISS_LOADING) }
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveMasterProfile successfully: ")
                },
                onError = {
                    Timber.tag(TAG).d("saveMasterProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveServiceArea() {
        Timber.tag(TAG).d("saveServiceArea: ")

        saveMasterUseCase(
            MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                serviceArea = _profile.value?.requiredInformation?.serviceArea,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveMasterProfile successfully: ")
                    requestProfile()
                },
                onError = {
                    Timber.tag(TAG).d("saveMasterProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"
    }
}