package kr.co.soogong.master.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.*
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirementList(masterId: Int): Single<List<RequirementDto>> {
        val query = HashMap<String, Any>()
        query["masterId"] = masterId
        query["statusArray"] =
            listOf(RequirementStatus.Requested.toString(), RequirementStatus.Estimated.toString())

        return requirementInterface.getRequirementList(query)
    }

    fun getProgressList(masterId: Int): Single<List<RequirementDto>> {
        val query = HashMap<String, Any>()
        query["masterId"] = masterId
        query["statusArray"] = listOf(
            RequirementStatus.Repairing.toString(),
            RequirementStatus.RequestFinish.toString()
        )

        return requirementInterface.getRequirementList(query)
    }

    fun getDoneList(masterId: Int): Single<List<RequirementDto>> {
        val query = HashMap<String, Any>()
        query["masterId"] = masterId
        query["statusArray"] = listOf(
            RequirementStatus.Done.toString(),
            RequirementStatus.Closed.toString(),
            RequirementStatus.CanceledByClient.toString(),
            RequirementStatus.CanceledByMaster.toString(),
            RequirementStatus.Canceled.toString(),
            RequirementStatus.Impossible.toString(),
            RequirementStatus.Failed.toString(),
        )

        return requirementInterface.getRequirementList(query)
    }

    fun refuseToEstimate(branchKeycode: String?, id: Int): Single<Response> {
        val data = HashMap<String, Any?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = id

        return requirementInterface.refuseToEstimate(data)
    }

    fun sendMessage(
        branchKeycode: String?,
        keycode: String,
        transmissionType: String,
        estimationMessage: EstimationMessage
    ): Single<Response> {
        val data = HashMap<String, String?>()
        data["action_type"] = "accept"
        data["branch_keycode"] = branchKeycode
        data["keycode"] = keycode
        data["transmission_type"] = transmissionType
        data["contents"] = estimationMessage.description
        data["price_in_number"] = estimationMessage.totalPrice
        data["personnel"] = estimationMessage.personnel
        data["material"] = estimationMessage.material
        data["trip"] = estimationMessage.trip
        return requirementInterface.sendMessage(data)
    }

    fun cancelEstimate(
        branchKeycode: String?,
        estimationId: Int,
        cancelEstimate: CancelEstimate
    ): Single<Response> {
        val data = HashMap<String, Any?>()
        data["action_type"] = "refuse"
        data["branch_keycode"] = branchKeycode
        data["keycode"] = estimationId
        data["message"] = cancelEstimate.message
        data["sub_message"] = cancelEstimate.subMessage
        return requirementInterface.cancelEstimate(data)
    }

    fun endEstimate(
        branchKeycode: String?,
        estimationId: Int,
        endEstimate: EndEstimate
    ): Single<Response> {
        val data = HashMap<String, Any?>()
        data["branch_keycode"] = branchKeycode
        data["keycode"] = estimationId
        data["actual_date"] = endEstimate.actualDate
        data["actual_price"] = endEstimate.actualPrice
        return requirementInterface.endEstimate(data)
    }

    fun callToCustomer(
        id: Int,
    ): Single<Response> {
        val data = HashMap<String, Any>()
        data["keycode"] = id
        return requirementInterface.callToCustomer(data)
    }

    fun askForReview(
        id: Int,
        branchKeycode: String?
    ): Single<Response> {
        val data = HashMap<String, Any?>()
        data["keycode"] = id
        data["branch_keycode"] = branchKeycode
        return requirementInterface.askForReview(data)
    }
}