package kr.co.soogong.master.data.model.requirement

import java.util.*

data class RequirementCard(
    val keyCode: String,
    val address: String,
    val project: String,
    val createdAt: Date,
    var status: EstimationStatus,
    var statusDetailText: String,
    val transmissions: Transmissions
) {
    companion object {
        fun from(estimation: Estimation?): RequirementCard {
            estimation ?: return NULL_CARD

            val detailText: String
            val status: EstimationStatus
            when (estimation.status) {
                "delivered" -> {
                    when (estimation.transmissions.status) {
                        "refused" -> {
                            detailText = ""
                            status = EstimationStatus.Cancel
                        }
                        "accepted" -> {
                            detailText = "고객의 선택을 기다려주세요"
                            status = EstimationStatus.Waiting
                        }
                        else -> {
                            detailText = "견적서를 작성해주세요"
                            status = EstimationStatus.Request
                        }
                    }
                }
                "customer_transfered" -> {
                    when (estimation.transmissions.status) {
                        "refused", "expired" -> {
                            detailText = ""
                            status = EstimationStatus.Cancel
                        }
                        else -> {
                            detailText = "고객의 선택을 기다려주세요"
                            status = EstimationStatus.Waiting
                        }
                    }
                }
                "reserved" -> {
                    detailText = ""
                    status = EstimationStatus.Progress
                }
                "custom_done" -> {
                    detailText = "수리를 완료하고 리뷰를 쌓아보세요"
                    status = EstimationStatus.Progress
                }
                "reviewed" -> {
                    detailText = "고객에게 리뷰요청을 해주세요"
                    status = EstimationStatus.Done
                }
                "closed" -> {
                    detailText = "고객이 리뷰를 남겼어요"
                    status = EstimationStatus.Final
                }
                else -> {
                    detailText = ""
                    status = EstimationStatus.Cancel
                }
            }
            return RequirementCard(
                keyCode = estimation.keycode,
                address = estimation.address,
                project = estimation.project,
                createdAt = Date(estimation.createdAt),
                status = status,
                statusDetailText = detailText,
                transmissions = estimation.transmissions
            )
        }

        private val NULL_CARD =
            RequirementCard(
                "",
                "",
                "",
                Date(),
                EstimationStatus.Cancel,
                "",
                Transmissions("", null, "")
            )
    }
}