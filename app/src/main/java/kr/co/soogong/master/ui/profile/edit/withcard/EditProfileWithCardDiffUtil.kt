package kr.co.soogong.master.ui.profile.edit.withcard

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.model.profile.IPortfolio

class EditProfileWithCardDiffUtil : DiffUtil.ItemCallback<IPortfolio>() {
    override fun areItemsTheSame(oldItem: IPortfolio, newItem: IPortfolio): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IPortfolio, newItem: IPortfolio): Boolean {
        return oldItem == newItem
    }
}