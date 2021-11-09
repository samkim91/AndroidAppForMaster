package kr.co.soogong.master.ui.major.project

import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Project
import timber.log.Timber

@BindingAdapter("bind:project_list")
fun RecyclerView.setList(items: List<Project>?) {
    Timber.tag("TAG").d("setList: $items")
    (adapter as? ProjectAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("bind:select_text")
fun Button.setSelectText(list: List<Project>?) {
    when{
        list.isNullOrEmpty() || list.none { it.checked } -> {
            text = "전문 분야를 모두 선택해주세요"
            setBackgroundColor(resources.getColor(R.color.c_90E9BD, null))
        }
        else -> {
            text = "${list.filter { it.checked }.size}개 선택 완료"
            setBackgroundColor(resources.getColor(R.color.c_22D47B, null))
        }
    }
}