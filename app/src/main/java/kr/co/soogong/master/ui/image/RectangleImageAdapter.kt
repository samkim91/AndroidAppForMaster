package kr.co.soogong.master.ui.image

import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.estimation.ImagePath
import kr.co.soogong.master.util.extension.dp

class RectangleImageAdapter(
    private val cardClickListener: ((Int) -> Unit),
) : RecyclerView.Adapter<RectangleImageViewHolder>() {
    private val imageList: ArrayList<ImagePath> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RectangleImageViewHolder =
        RectangleImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: RectangleImageViewHolder, position: Int) {
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(2.dp)
        holder.itemView.layoutParams = params

        holder.binding(imageList[position].path)
        holder.binding.setCardClickListener {
            cardClickListener(position)
        }
    }

    override fun getItemCount(): Int = imageList.size

    fun setList(imageList: List<ImagePath>) {
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}