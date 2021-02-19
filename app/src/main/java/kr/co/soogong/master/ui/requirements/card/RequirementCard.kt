package kr.co.soogong.master.ui.requirements.card

data class RequirementCard(
    val status: RequirementStatus
)

sealed class RequirementStatus {
    object Request : RequirementStatus()
    object Waiting : RequirementStatus()
    object Progress : RequirementStatus()
    object CustomDone : RequirementStatus()
    object Done : RequirementStatus()
    object Final : RequirementStatus()
    object Cancel : RequirementStatus()
}