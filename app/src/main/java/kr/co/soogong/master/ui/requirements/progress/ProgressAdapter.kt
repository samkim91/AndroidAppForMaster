package kr.co.soogong.master.ui.requirements.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewholderProgressItemBinding

class ProgressAdapter : ListAdapter<ProgressCard, ProgressViewHolder>(ProgressCardDiffUtil()) {

    lateinit var callButtonClick: (String) -> Unit
    lateinit var detailButtonClick: (Long) -> Unit
    lateinit var removeButtonClick: (Long) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val binding = DataBindingUtil.inflate<ViewholderProgressItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.viewholder_progress_item,
            parent,
            false
        )
        return ProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.binding.run {
            val item = currentList[position]
            vm = item

            setCallBtnListener {
                callButtonClick(item.tel)
            }

            setDetailBtnListener {
                detailButtonClick(item.id)
            }

            setRemoveBtnListener {
                removeButtonClick(item.id)
            }

            executePendingBindings()
        }
    }

}