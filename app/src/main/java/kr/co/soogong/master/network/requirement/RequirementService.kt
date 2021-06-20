package kr.co.soogong.master.network.requirement

import io.reactivex.Single
import kr.co.soogong.master.data.dto.Response
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.CancelEstimate
import kr.co.soogong.master.data.model.requirement.EndEstimate
import kr.co.soogong.master.data.model.requirement.EstimationMessage
import retrofit2.Retrofit
import javax.inject.Inject

class RequirementService @Inject constructor(
    retrofit: Retrofit
) {
    private val requirementInterface = retrofit.create(RequirementInterface::class.java)

    fun getRequirementList(masterId: Int, statusArray: List<String>): Single<List<RequirementDto>> {
        return requirementInterface.getRequirementList(masterId, statusArray)
    }

    fun getRequirement(requirementId: Int, masterId: Int): Single<RequirementDto> {
        return requirementInterface.getRequirement(requirementId, masterId)
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