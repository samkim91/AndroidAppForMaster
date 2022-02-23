package kr.co.soogong.master.presentation.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.entity.profile.MasterDto
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.domain.entity.profile.Profile
import kr.co.soogong.master.domain.usecase.profile.GetProfileUseCase
import kr.co.soogong.master.domain.usecase.profile.SaveMasterUseCase
import kr.co.soogong.master.presentation.ui.base.BaseViewModel
import kr.co.soogong.master.presentation.ui.profile.detail.CareerConverter
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val saveMasterUseCase: SaveMasterUseCase,
) : BaseViewModel() {
    private val _isShownRequiredFieldDialog = MutableLiveData(false)
    val isShownRequiredFieldDialog: LiveData<Boolean>
        get() = _isShownRequiredFieldDialog

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile>
        get() = _profile

    val ownerName = MutableLiveData("")
    val profileImage = MutableLiveData(Uri.EMPTY)

    val percentageBasic = MutableLiveData<Double>()
    val percentageRequired = MutableLiveData<Double>()

    fun setIsShownRequiredFieldDialog(boolean: Boolean) {
        _isShownRequiredFieldDialog.value = boolean
    }

    fun requestProfile() {
        Timber.tag(TAG).d("requestProfile: ")
        getProfileUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("requestProfile Successfully: ")
                    _profile.value = it
                    it.requiredInformation.ownerName.run {
                        ownerName.value = this
                    }       // 2-way binding 을 위한 set
                },
                onError = {
                    Timber.tag(TAG).d("requestProfile Failed: $it")
                    setAction(REQUEST_FAILED)
                }
            )
            .addToDisposable()
    }

    fun saveOwnerName() {
        Timber.tag(TAG).d("saveOwnerName: ")
        saveMasterUseCase(
            masterDto = MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                ownerName = ownerName.value
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Timber.tag(TAG).d("saveMasterProfile successfully: ") },
                onError = {
                    Timber.tag(TAG).d("saveMasterProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun saveMasterProfileImage() {
        Timber.tag(TAG).d("saveMasterProfileImage: ")
        saveMasterUseCase(
            masterDto = MasterDto(
                id = _profile.value?.id,
                uid = _profile.value?.uid,
                approvedStatus = if (_profile.value?.approvedStatus == CodeTable.APPROVED) CodeTable.REQUEST_APPROVE.code else null,
            ),
            profileImageUri = profileImage.value,
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setAction(SHOW_LOADING) }
            .doAfterTerminate { setAction(DISMISS_LOADING) }
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

    fun saveCareerPeriod(careerPeriod: Int) {
        Timber.tag(TAG).d("saveCareerPeriod: ")

        saveMasterUseCase(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                openDate = CareerConverter.toOpenDate(careerPeriod),
                approvedStatus = if (profile.value?.approvedStatus == CodeTable.APPROVED) CodeTable.REQUEST_APPROVE.code else null,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setAction(SHOW_LOADING) }
            .doAfterTerminate { setAction(DISMISS_LOADING) }
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
            .doOnSubscribe { setAction(SHOW_LOADING) }
            .doAfterTerminate { setAction(DISMISS_LOADING) }
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveServiceArea onSuccess: ")
                    requestProfile()
                },
                onError = {
                    Timber.tag(TAG).d("saveMasterProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun requestApprove() {
        Timber.tag(TAG).d("requestApprove: ")
        saveMasterUseCase(
            MasterDto(
                id = profile.value?.id,
                uid = profile.value?.uid,
                approvedStatus = CodeTable.REQUEST_APPROVE.code,
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.tag(TAG).d("saveMasterProfile successfully: ")
                    setAction(REQUEST_APPROVE_SUCCESSFULLY)
                },
                onError = {
                    Timber.tag(TAG).d("saveMasterProfile failed: $it")
                    setAction(REQUEST_FAILED)
                }
            ).addToDisposable()
    }

    fun onClickRequestReview() =
        setAction(ON_CLICK_REQUEST_REVIEW).apply { Timber.tag(TAG).d("onClickRequestReview: ") }

    fun onClickShowMyProfile() =
        setAction(ON_CLICK_SHOW_MY_PROFILE).apply { Timber.tag(TAG).d("onClickShowMyProfile: ") }

    companion object {
        private const val TAG = "ProfileViewModel"
        const val REQUEST_FAILED = "REQUEST_FAILED"

        const val REQUEST_APPROVE_SUCCESSFULLY = "REQUEST_APPROVE_SUCCESSFULLY"

        const val ON_CLICK_REQUEST_REVIEW = "ON_CLICK_REQUEST_REVIEW"
        const val ON_CLICK_SHOW_MY_PROFILE = "ON_CLICK_SHOW_MY_PROFILE"
    }
}