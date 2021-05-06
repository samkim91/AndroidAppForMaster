package kr.co.soogong.master.ui.widget.titlerecyclerimage

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import kr.co.soogong.master.databinding.ViewTitleRecyclerImageBinding
import kr.co.soogong.master.ui.utils.ListLiveData

class TitleRecyclerImage @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleRecyclerImageBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = true
        set(value) {
            field = value
            binding.title.visibility = if (value) View.VISIBLE else View.GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.text = value
        }

    var subTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.subTitle.visibility = if (value) View.VISIBLE else View.GONE
        }

    var alertText: String? = ""
        set(value) {
            field = value
            binding.alert.text = value
        }

    var alertVisible: Boolean = false
        set(value) {
            field = value
            binding.alert.visibility = if (value) View.VISIBLE else View.GONE
        }

    var cameraIconVisible: Boolean = true
        set(value) {
            field = value
            binding.cameraIcon.visibility = if (value) View.VISIBLE else View.GONE
        }

    var photoListVisible: Boolean = false
        set(value) {
            field = value
            binding.photoList.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun setAdapter(
        closeClick: (Int) -> Unit
    ){
        binding.photoList.adapter = TitleRecyclerImageAdapter(closeClickListener = { position ->
            closeClick(position)
        })
    }

    fun replaceItems(
        items: MutableList<Uri>?
    ){
        (binding.photoList.adapter as? TitleRecyclerImageAdapter)?.submitList(items)
        // Todo.. submitList로 item change event가 call 되지 않고 있음... 원인을 파악해봐야함. 아래 코드는 임시적으로 적용해둠.
        (binding.photoList.adapter as? TitleRecyclerImageAdapter)?.notifyDataSetChanged()
    }

    fun addIconClickListener(
        onClick: () -> Unit
    ) {
        binding.cameraIcon.setOnClickListener { onClick() }
    }

}