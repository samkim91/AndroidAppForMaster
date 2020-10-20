package kr.co.soogong.master.ui.requirements.received

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class ReceivedAdapter(
    private val buttonClick: (String) -> Unit
) : ListAdapter<ReceivedCard, ReceivedViewHolder>(ReceivedCardDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReceivedViewHolder.create(parent)

    override fun onBindViewHolder(holder: ReceivedViewHolder, position: Int) {
        holder.bind(getItem(position), buttonClick)
    }
}