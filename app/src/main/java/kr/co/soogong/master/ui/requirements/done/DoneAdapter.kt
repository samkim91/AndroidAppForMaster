package kr.co.soogong.master.ui.requirements.done

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.ui.requirements.progress.ProgressCard
import kr.co.soogong.master.ui.requirements.progress.ProgressCardDiffUtil
import kr.co.soogong.master.ui.requirements.progress.ProgressViewHolder

class DoneAdapter : ListAdapter<ProgressCard, ProgressViewHolder>(ProgressCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProgressViewHolder.create(parent)

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
//        holder.bind(getItem(position), callButtonClick, detailButtonClick, removeButtonClick)
    }
}