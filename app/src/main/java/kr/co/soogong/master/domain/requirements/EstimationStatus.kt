package kr.co.soogong.master.domain.requirements

import kr.co.soogong.master.data.estimation.Transmissions

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