package kr.co.soogong.master.ui.select.project

import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.category.Project
import timber.log.Timber

@BindingAdapter("bind:project_list")
fun RecyclerView.setList(items: List<Project>?) {
    Timber.tag("TAG").d("setList: $items")
    (adapter as? ProjectSelectAdapter)?.submitList(items ?: emptyList())
}

@BindingAdapter("bind:select_text")
fun Button.setSelectText(list: List<Project>?) {
    text = when {
        list.isNullOrEmpty() -> {
            "프로젝트를 선택해주세요"
        }
        list.none { it.checked } -> {
            "프로젝트를 선택해주세요"
        }
        else -> {
            "${list.filter { it.checked }.size}개 프로젝트 선택하기"
        }
    }
}