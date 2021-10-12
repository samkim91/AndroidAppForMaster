package kr.co.soogong.master.data.repository

import dagger.Reusable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
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

    fun getRequirementById(requirementId: Int): Single<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromAll start: $requirementId")
        return getRequirementFromServer(requirementId)
            .doOnError { Timber.tag(TAG).d("getRequirementById failed: $it") }
            .onErrorResumeNext(getRequirementFromLocal(requirementId))
    }

    private fun getRequirementFromLocal(requirementId: Int): Single<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromLocal start: $requirementId")
        return requirementDao.getItem(requirementId)
            .doOnSuccess {
                Timber.tag(TAG).d("getRequirementFromLocal: $it")
            }
    }

    private fun getRequirementFromServer(requirementId: Int): Single<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromServer start: $requirementId")
        return requirementService.getRequirement(requirementId, getMasterUidFromSharedUseCase()!!)
            .doOnSuccess {
                Timber.tag(TAG).d("getRequirementFromServer: $it ")
                saveRequirementInLocal(it)
            }
    }

    private fun saveRequirementInLocal(requirementDto: RequirementDto) {
        Timber.tag(TAG).d("saveRequirementInLocal start: ")
        val disposable = Observable.fromCallable {
            requirementDao.remove(requirementDto)
            requirementDao.insert(requirementDto)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeBy {
                Timber.tag(TAG).d("saveRequirementInLocal: ")
            }

        disposable.addTo(compositeDisposable)
    }

    fun getRequirementsByStatus(
        statusArray: List<String>
    ): Flowable<List<RequirementDto>> {
        return getRequirementsFromLocal(statusArray)
            .onErrorResumeNext(Flowable.empty())
            .concatWith(getRequirementsFromServer(statusArray))
    }

    private fun getRequirementsFromLocal(
        statusArray: List<String>
    ): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementsFromLocal start: $statusArray")
        return requirementDao.getListByStatus(statusArray)
            .filter { it.isNotEmpty() }
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementsFromLocal: ${it.size}")
            }
    }

    private fun getRequirementsFromServer(statusArray: List<String>): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementFromServer start: $statusArray")
        return requirementService.getRequirementsByStatus(
            getMasterUidFromSharedUseCase()!!,
            statusArray
        )
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementsFromServer: ${it.size}")
                saveRequirementsInLocal(statusArray, it)
            }
    }

    private fun saveRequirementsInLocal(statusArray: List<String>, newList: List<RequirementDto>?) {
        Timber.tag(TAG).d("saveRequirementsInLocal start: ")
        val disposable =
            Observable.fromCallable {
                requirementDao.removeByStatus(statusArray)
                newList?.toTypedArray()?.let { requirementDao.insertAll(*it) }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeBy {
                    Timber.tag(TAG).d("saveRequirementsInLocal: ")
                }

        disposable.addTo(compositeDisposable)
    }

    fun searchRequirements(
        searchingText: String,
        searchingPeriod: Int,
    ): Flowable<List<RequirementDto>> {
        return searchRequirementsFromServer(searchingText, searchingPeriod)
    }

    private fun searchRequirementsFromServer(
        searchingText: String,
        searchingPeriod: Int,
    ): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("searchRequirementsFromServer start: $searchingText, $searchingPeriod")
        return requirementService.searchRequirements(
            getMasterUidFromSharedUseCase()!!,
            searchingText,
            searchingPeriod
        )
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("searchRequirementsFromServer: ${it.size}")
            }
    }

    companion object {
        private const val TAG = "RequirementRepository"
    }
}
