package kr.co.soogong.master.ui.requirements.progress

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ProgressAdapter(
    val callButtonClick: (String) -> Unit,
    val detailButtonClick: (String) -> Unit,
    val removeButtonClick: (String) -> Unit
) : ListAdapter<ProgressCard, ProgressViewHolder>(ProgressCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProgressViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.bind(getItem(position), callButtonClick, detailButtonClick, removeButtonClick)
    }
}