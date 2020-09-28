package kr.co.soogong.master.ui.requirements.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewholderProgressItemBinding

class ProgressAdapter : ListAdapter<ProgressCard, ProgressViewHolder>(ProgressCardDiffUtil()) {

    lateinit var callButtonClick: (String) -> Unit
    lateinit var detailButtonClick: (String) -> Unit
    lateinit var removeButtonClick: (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val binding = ViewholderProgressItemBinding.inflate(
            LayoutInflater.from(parent.context),
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
                detailButtonClick(item.keycode)
            }

            setRemoveBtnListener {
                removeButtonClick(item.keycode)
            }

            executePendingBindings()
        }
    }

}