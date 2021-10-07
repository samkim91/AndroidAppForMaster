package kr.co.soogong.master.ui.image

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.AttachmentDto
import timber.log.Timber

@BindingAdapter("bind:photo_list_with_close")
fun RecyclerView.setClosableList(items: List<AttachmentDto>?) {
    // DiffUtil.ItemCallback을 호출하지 못하는 문제가 있어서, toMutableList 를 추가해놨다.
    // 참고 : https://stackoverflow.com/questions/49726385/listadapter-not-updating-item-in-recyclerview?noredirect=1&lq=1 의 3번째 답변
    (adapter as? RectangleImageWithCloseAdapter)?.submitList(items?.toMutableList() ?: emptyList())
}