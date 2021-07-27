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
import kr.co.soogong.master.data.model.requirement.RequirementCard
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

    fun getRequirements(statusArray: List<String>): Flowable<List<RequirementCard>> {
        return Flowable.concatArray(
            getRequirementsFromLocal(statusArray),
            getRequirementsFromServer(statusArray)
        ).map { dtoList ->
            dtoList.map { dto ->
                RequirementCard.fromRequirementDto(dto)
            }
        }
    }

    fun getRequirementsFromLocalAsCards(statusArray: List<String>): Single<List<RequirementCard>> {
        return getRequirementsFromLocal(statusArray)
            .firstOrError()
            .map { dtoList ->
                dtoList.map { dto ->
                    RequirementCard.fromRequirementDto(dto)
                }
            }
    }

    private fun getRequirementsFromLocal(statusArray: List<String>): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementsFromDb start: ")
        return requirementDao.getListByStatusTest(statusArray, false)
            .filter { it.isNotEmpty() }
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementFromDb: ${it.size}")
            }
    }

    private fun getRequirementsFromServer(statusArray: List<String>): Flowable<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementFromServer start: ")
        return requirementService.getRequirementList(getMasterUidFromSharedUseCase()!!, statusArray)
            .toFlowable()
            .doOnNext {
                Timber.tag(TAG).d("getRequirementFromServer: ${it.size}")
                saveRequirementsInDb(it)
            }
    }

    private fun saveRequirementsInDb(list: List<RequirementDto>?) {
        Timber.tag(TAG).d("saveRequirementsInDb start: ")
        val disposable =
            Observable.fromCallable { list?.toTypedArray()?.let { requirementDao.insertAll(*it) } }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeBy {
                    Timber.tag(TAG).d("saveRequirementsInDb: ${it}")
                }

        disposable.addTo(compositeDisposable)
    }

    companion object {
        private const val TAG = "RequirementRepository"
    }
}
