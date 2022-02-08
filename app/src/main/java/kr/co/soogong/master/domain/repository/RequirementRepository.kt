package kr.co.soogong.master.domain.repository

import dagger.Reusable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.data.local.requirement.RequirementDao
import kr.co.soogong.master.data.entity.common.PageableContentDto
import kr.co.soogong.master.data.entity.common.ResponseDto
import kr.co.soogong.master.data.entity.requirement.RequirementCardDto
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.domain.usecase.auth.GetMasterUidFromSharedUseCase
import kr.co.soogong.master.data.network.requirement.RequirementService
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
//            .onErrorResumeNext(getRequirementFromLocal(requirementId))
            .doOnError { Timber.tag(TAG).d("getRequirementById failed: ") }
    }

    private fun getRequirementFromLocal(requirementId: Int): Single<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromLocal start: $requirementId")
        return requirementDao.getItem(requirementId)
            .doOnSuccess {
                Timber.tag(TAG).d("getRequirementFromLocal: ")
            }
    }

    private fun getRequirementFromServer(requirementId: Int): Single<RequirementDto> {
        Timber.tag(TAG).d("getRequirementFromServer start: $requirementId")
        return requirementService.getRequirement(requirementId, getMasterUidFromSharedUseCase()!!)
//            .doOnSuccess {
//                Timber.tag(TAG).d("getRequirementFromServer: ")
//                saveRequirementInLocal(it)
//            }
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
        status: String,
        readYns: Boolean?,
        offset: Int,
        pageSize: Int,
        order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>> {
        return getRequirementsFromServer(status, readYns, offset, pageSize, order)
//            .onErrorResumeNext(getRequirementsFromLocal(statusArray))
            .doOnError { Timber.tag(TAG).d("getRequirementsByStatus failed: $it") }
    }

    private fun getRequirementsFromLocal(
        statusArray: List<String>,
    ): Single<List<RequirementDto>> {
        Timber.tag(TAG).d("getRequirementsFromLocal start: $statusArray")
        return requirementDao.getListByStatus(statusArray)
            .doOnSuccess {
                Timber.tag(TAG).d("getRequirementsFromLocal: ")
            }
    }

    private fun getRequirementsFromServer(
        status: String,
        readYns: Boolean?,
        offset: Int,
        pageSize: Int,
        order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>> {
        Timber.tag(TAG).d("getRequirementFromServer start: $status")
        return requirementService.getRequirements(getMasterUidFromSharedUseCase()!!,
            status,
            readYns,
            offset,
            pageSize,
            order)
            .doOnSuccess {
                Timber.tag(TAG).d("getRequirementsFromServer: ")
//                saveRequirementsInLocal(status, it)
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

    fun searchRequirementsFromServer(
        searchingText: String,
        searchingPeriod: Int,
        readYns: Boolean? = null,
        offset: Int,
        pageSize: Int,
        order: Int,
    ): Single<ResponseDto<PageableContentDto<RequirementCardDto>>> {
        Timber.tag(TAG).d("searchRequirementsFromServer start: $searchingText, $searchingPeriod")
        return requirementService.searchRequirements(
            getMasterUidFromSharedUseCase()!!,
            searchingText,
            searchingPeriod,
            readYns,
            offset,
            pageSize,
            order
        )
            .doOnSuccess {
                Timber.tag(TAG).d("searchRequirementsFromServer: ")
            }
    }

    companion object {
        private const val TAG = "RequirementRepository"
    }
}
