package kr.co.soogong.master.ui.image

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ortiz.touchview.TouchImageView
import kr.co.soogong.master.data.dto.common.AttachmentDto
import timber.log.Timber

class ImageSliderAdapter : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    private val imageList: MutableList<AttachmentDto> = mutableListOf()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(TouchImageView(parent.context).apply {
            Timber.tag(TAG).d("onCreateViewHolder: ")

            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            // TouchImageview library 에서 ViewPager2 를 사용할 때, 아래 처리를 해줘야한다.
            setOnTouchListener { view, event ->
                var result = true
                //can scroll horizontally checks if there's still a part of the image
                //that can be scrolled until you reach the edge
                if (event.pointerCount >= 2 || view.canScrollHorizontally(1) && canScrollHorizontally(
                        -1
                    )
                ) {
                    //multi-touch event
                    result = when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                            // Disallow RecyclerView to intercept touch events.
                            parent.requestDisallowInterceptTouchEvent(true)
                            // Disable touch on view
                            false
                        }
                        MotionEvent.ACTION_UP -> {
                            // Allow RecyclerView to intercept touch events.
                            parent.requestDisallowInterceptTouchEvent(false)
                            true
                        }
                        else -> true
                    }
                }
                result
            }
        })
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Timber.tag(TAG).d("onBindViewHolder: $position")
        Glide.with(holder.itemView.context)
            .load(imageList[position].url)
            .into(object : CustomTarget<Drawable?>() {
                // 보통 Glide 를 사용할 땐, into(URL)을 넣어주면 되나, 여기선 TouchImageview 를 사용하다보니, 아래와 같은 방법으로
                // URL Drawable 을 뽑은 뒤 넣어주고 있다.
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?,
                ) {
                    Timber.tag(TAG).d("onResourceReady: $position")
                    holder.imagePlace.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

    override fun getItemCount(): Int = imageList.size

    fun setList(images: List<AttachmentDto>) {
        Timber.tag(TAG).d("setList: ${images.size}")
        this.imageList.clear()
        this.imageList.addAll(images)
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "ImageSliderAdapter"
    }

    class ImageViewHolder(view: TouchImageView) : RecyclerView.ViewHolder(view) {
        val imagePlace = view
    }
}