package kr.co.soogong.master.ui.image

import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.utility.extension.dp

class RectangleImageAdapter(
    private val cardClickListener: ((Int) -> Unit),
) : RecyclerView.Adapter<RectangleImageViewHolder>() {
    private val images: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RectangleImageViewHolder =
        RectangleImageViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: RectangleImageViewHolder, position: Int) {
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(2.dp)
        holder.itemView.layoutParams = params

        images[position].run {
            holder.binding(this)
            holder.binding.setCardClickListener { cardClickListener(position) }
        }
    }

    override fun getItemCount(): Int = images.size

    fun setImageUrls(imageUrls: List<String>) {
        this.images.clear()
        this.images.addAll(imageUrls)
        notifyDataSetChanged()
    }
}