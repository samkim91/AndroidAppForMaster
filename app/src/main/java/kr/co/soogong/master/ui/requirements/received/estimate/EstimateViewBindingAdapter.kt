package kr.co.soogong.master.ui.requirements.received.estimate

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.data.requirements.Requirement

@BindingAdapter("bind:estimate_title")
fun TextView.setEstimateTitle(requirement: Requirement?) {
    requirement?.let {
        text = "${requirement.location} ${requirement.category} \n 수리 요청에 대한 견적서"
    } ?: run {
        text = ""
    }
}

@BindingAdapter("bind:estimate_detail")
fun TextView.setEstimateDetail(requirement: Requirement?) {
    requirement?.let {
        text = "${requirement.houseType} / ${requirement.size} / ${requirement.content}"
    } ?: run {
        text = ""
    }
}

@BindingAdapter("bind:estimate_amount")
fun EditText.setEstimateAmount(amount: String) {

}