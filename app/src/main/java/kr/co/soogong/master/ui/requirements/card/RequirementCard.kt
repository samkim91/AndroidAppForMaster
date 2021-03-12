package kr.co.soogong.master.ui.requirements.card

import kr.co.soogong.master.data.estimation.Estimation
import kr.co.soogong.master.data.estimation.Transmissions
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

sealed class EstimationStatus {
    abstract fun toInt(): Int

    object Request : EstimationStatus() {
        override fun toString(): String {
            return "견적요청"
        }

        override fun toInt(): Int = 100
    }

    object Waiting : EstimationStatus() {
        override fun toString(): String {
            return "매칭대기"
        }

        override fun toInt(): Int = 200
    }

    object Progress : EstimationStatus() {
        override fun toString(): String {
            return "시공진행중"
        }

        override fun toInt(): Int = 300
    }

    object CustomDone : EstimationStatus() {
        override fun toString(): String {
            return "고객완료요청"
        }

        override fun toInt(): Int = 400
    }

    object Done : EstimationStatus() {
        override fun toString(): String {
            return "시공완료"
        }

        override fun toInt(): Int = 500
    }

    object Final : EstimationStatus() {
        override fun toString(): String {
            return "평가완료"
        }

        override fun toInt(): Int = 600
    }

    object Cancel : EstimationStatus() {
        override fun toString(): String {
            return "시공취소"
        }

        override fun toInt(): Int = 700
    }

    companion object {
        fun getStatus(status: String?, transmissions: Transmissions?): EstimationStatus {
            when (status) {
                "delivered" -> {
                    return when (transmissions?.status) {
                        "refused" -> {
                            Cancel
                        }
                        "accepted" -> {
                            Waiting
                        }
                        else -> {
                            Request
                        }
                    }
                }
                "customer_transfered" -> {
                    return when (transmissions?.status) {
                        "refused", "expired" -> {
                            Cancel
                        }
                        else -> {
                            Waiting
                        }
                    }
                }
                "reserved" -> {
                    return Progress
                }
                "reviewed" -> {
                    return Done
                }
                "closed" -> {
                    return Final
                }
                else -> {
                    return Cancel
                }
            }
        }
    }
}