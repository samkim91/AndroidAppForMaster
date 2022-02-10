package kr.co.soogong.master.presentation.ui.requirement

import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.entity.requirement.repair.RepairDto

interface IRequirementViewModel {

    fun callToClient(estimationId: Int) {}

    fun respondToMeasure(estimationDto: EstimationDto)

    fun askForReview(repairDto: RepairDto)
}