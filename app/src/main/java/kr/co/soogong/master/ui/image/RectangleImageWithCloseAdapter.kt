package kr.co.soogong.master.ui.image

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewHolderImageDeleteButtonBinding
import kr.co.soogong.master.utility.extension.dp

class RectangleImageWithCloseAdapter(
    private val closeClickListener: (Int) -> Unit,
) : ListAdapter<Uri, RectangleImageWithCloseHolder>(RectangleImageWithCloseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RectangleImageWithCloseHolder(ViewHolderImageDeleteButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: RectangleImageWithCloseHolder, position: Int) {
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.marginEnd = 10.dp
        params.bottomMargin = 10.dp
        holder.itemView.layoutParams = params

        holder.binding(currentList[position], closeClickListener)
    }

}