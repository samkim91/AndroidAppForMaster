package kr.co.soogong.master.ui.image

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

class RectangleImageWithCloseDiffUtil : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return newItem == oldItem
    }
}