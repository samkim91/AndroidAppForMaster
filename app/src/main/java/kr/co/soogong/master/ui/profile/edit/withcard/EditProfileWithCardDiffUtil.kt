package kr.co.soogong.master.ui.profile.edit.withcard

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.profile.IEditProfileWithCard

class EditProfileWithCardDiffUtil : DiffUtil.ItemCallback<IEditProfileWithCard>() {
    override fun areItemsTheSame(oldItem: IEditProfileWithCard, newItem: IEditProfileWithCard): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: IEditProfileWithCard, newItem: IEditProfileWithCard): Boolean {
        return oldItem == newItem
    }
}