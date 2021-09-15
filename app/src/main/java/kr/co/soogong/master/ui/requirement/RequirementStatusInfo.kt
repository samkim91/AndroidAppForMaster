package kr.co.soogong.master.ui.requirement

import kr.co.soogong.master.data.model.requirement.*

val allCodes = listOf(
    Requested.code,
    Estimated.code,
    Measuring.code,
    Measured.code,
    Repairing.code,
    RequestFinish.code,
    Done.code,
    Closed.code,
    Canceled.code,
)

val receivedStatus = listOf(
    Requested,
    Estimated,
    RequestConsult,
)

val receivedCodes = listOf(
    Requested.code,
    Estimated.code,
)

val progressStatus = listOf(
    Measuring,
    Measured,
    Repairing,
    RequestFinish,
)

val progressCodes = listOf(
    Measuring.code,
    Measured.code,
    Repairing.code,
    RequestFinish.code,
)

val doneStatus = listOf(
    Done,
    Closed,
    Canceled,
)

val doneCodes = listOf(
    Done.code,
    Closed.code,
    Canceled.code,
)
