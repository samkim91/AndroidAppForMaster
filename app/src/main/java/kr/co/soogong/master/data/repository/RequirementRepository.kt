package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.dao.requirement.RequirementDao
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.network.requirement.RequirementService
import timber.log.Timber
import javax.inject.Inject

@Reusable
class RequirementRepository @Inject constructor(
    private val requirementService: RequirementService,
    private val requirementDao: RequirementDao,
    private val getMasterUidFromSharedUseCase: GetMasterUidFromSharedUseCase,
) {
    private val compositeDisposable = CompositeDisposable()

    fun getRequirementFromAll(requirementId: Int): Flowable<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromAll start: $requirementId")
        return getRequirementFromLocal(requirementId)
            .concatWith(getRequirementFromServer(requirementId))
    }

    private fun getRequirementFromLocal(requirementId: Int): Flowable<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromLocal start: $requirementId")
        return requirementDao.getItem(requirementId)
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementFromLocal: $it")
            }
    }

    private fun getRequirementFromServer(requirementId: Int): Flowable<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromServer start: $requirementId")
        return requirementService.getRequirement(requirementId, getMasterUidFromSharedUseCase()!!)
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementFromServer: $it ")
                saveRequirementInLocal(it)
            }
    }

    private fun saveRequirementInLocal(requirementDto: RequirementDto) {
        Timber.tag(TAG).d("saveRequirementInLocal start: ")
        val disposable = Observable.fromCallable {
            requirementDao.insert(requirementDto)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy {
                Timber.tag(TAG).d("saveRequirementInLocal: $it")
            }

        disposable.addTo(compositeDisposable)
    }

    fun getRequirementsFromAll(
        statusArray: List<String>,
        canceledYn: Boolean = false
    ): Flowable<List<RequirementDto>> {
        return Flowable.concatArray(
            getRequirementsFromLocal(statusArray, canceledYn),
            getRequirementsFromServer(statusArray)
        )
    }

    fun getRequirementsFromLocal(
        statusArray: List<String>,
        canceledYn: Boolean = false
    ): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementsFromLocal start: $statusArray $canceledYn")
        return if (canceledYn) {
            requirementDao.getListByStatusIncludingCanceled(statusArray, canceledYn)
        } else {
            requirementDao.getListByStatus(statusArray, canceledYn)
        }
            .filter { it.isNotEmpty() }
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementsFromLocal: ${it.size}")
            }
    }

    private fun getRequirementsFromServer(statusArray: List<String>): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementFromServer start: $statusArray")
        return requirementService.getRequirementList(getMasterUidFromSharedUseCase()!!, statusArray)
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementFromServer: ${it.size}")
                saveRequirementsInLocal(it)
            }
    }

    private fun saveRequirementsInLocal(list: List<RequirementDto>?) {
        Timber.tag(TAG).d("saveRequirementsInLocal start: ")
        val disposable =
            Observable.fromCallable { list?.toTypedArray()?.let { requirementDao.insertAll(*it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeBy {
                    Timber.tag(TAG).d("saveRequirementsInLocal: $it")
                }

        disposable.addTo(compositeDisposable)
    }

    companion object {
        private const val TAG = "RequirementRepository"
    }
}
