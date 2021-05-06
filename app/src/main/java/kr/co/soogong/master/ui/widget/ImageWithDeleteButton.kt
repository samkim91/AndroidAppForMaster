package kr.co.soogong.master.ui.widget

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import kr.co.soogong.master.databinding.ViewHolderImageDeleteButtonBinding

class ImageWithDeleteButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : CardView(context, attributeSet, defStyle) {

    private val binding =
        ViewHolderImageDeleteButtonBinding.inflate(LayoutInflater.from(context), this, true)

    var imageUri: Uri = Uri.EMPTY
        set(value) {
            field = value
            binding.imageUri = value
        }

    var closeClickListener: OnClickListener ?= null
        set(value) {
            field = value
            binding.closeClickListener = value
        }


}