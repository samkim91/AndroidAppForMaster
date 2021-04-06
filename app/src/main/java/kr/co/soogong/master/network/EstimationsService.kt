package kr.co.soogong.master.network

import io.reactivex.Single
import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.data.requirements.Requirement
import retrofit2.Retrofit
import javax.inject.Inject

class EstimationsService @Inject constructor(
    retrofit: Retrofit
) {
    private val estimationsInterface = retrofit.create(EstimationsInterface::class.java)

    suspend fun getEstimationList(keycode: String?): List<Estimation> {
        val jsonObject = estimationsInterface.getEstimationList(keycode)

        val list = jsonObject.get("data").asJsonArray
        val ret = ArrayList<Estimation>()
        for (item in list) {
            ret.add(Estimation.fromJson(item.asJsonObject))
        }

        return ret
    }

    fun getRequirementList(keycode: String?): Single<List<Requirement>> {
        return estimationsInterface.getRequirementList(keycode)
            .map { list -> list.map { Requirement.fromJson(it, "received") } }
    }

    fun getProgressList(keycode: String?): Single<List<Requirement>> {
        return estimationsInterface.getProgressList(keycode)
            .map { list -> list.map { Requirement.fromJson(it, "progress") } }
    }

    fun refuseToEstimate(branchKeycode: String?, keycode: String): Single<Response> {
        val data = HashMap<String, String?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode

        return estimationsInterface.refuseToEstimate(data)
    }

    fun sendMessage(
        branchKeycode: String?,
        keycode: String,
        estimate: Estimate
    ): Single<String> {
        val data = HashMap<String, String?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode
        data["price"] = estimate.price
        data["contents"] = estimate.contents
        data["possible_date"] = estimate.possibleDate
        return estimationsInterface.sendMessage(data)
    }
}