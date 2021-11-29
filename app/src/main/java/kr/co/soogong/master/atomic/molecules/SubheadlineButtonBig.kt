package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.atomic.atoms.IButton
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ViewSubheadlineButtonBigBinding

class SubheadlineButtonBig @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IButton {
    private var binding =
        ViewSubheadlineButtonBigBinding.inflate(LayoutInflater.from(context), this, true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    var hint: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvFootnote.text = it
                binding.tvFootnote.isVisible = it.isNotEmpty()
            }
        }

    override var buttonText: String? = null
        set(value) {
            field = value
            value?.let { binding.bbButton.buttonText = value }
        }

    override var buttonEnable: Boolean? = null
        set(value) {
            field = value
            value?.let { binding.bbButton.buttonEnable = it }
        }

    override var buttonTheme: ButtonTheme? = null
        set(value) {
            field = value
            value?.let { binding.bbButton.buttonTheme = it }
        }

    override var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.bbButton.onButtonClick = it }
        }
}