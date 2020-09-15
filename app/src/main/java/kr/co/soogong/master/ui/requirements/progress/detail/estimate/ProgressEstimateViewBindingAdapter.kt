package kr.co.soogong.master.ui.requirements.progress.detail.estimate

import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.data.requirements.Requirement

@BindingAdapter("bind:progress_estimate_title")
fun TextView.setProgressEstimateTitle(requirement: Requirement?) {
    requirement?.let {
        text = "${requirement.location} ${requirement.category} \n수리 요청에 대한 견적서"
    } ?: run {
        text = ""
    }
}

@BindingAdapter("bind:progress_estimate_detail")
fun TextView.setProgressEstimateDetail(requirement: Requirement?) {
    requirement?.let {
        text = "${requirement.houseType} / ${requirement.size} / ${requirement.content}"
    } ?: run {
        text = ""
    }
}