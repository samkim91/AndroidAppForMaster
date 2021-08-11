package kr.co.soogong.master.ui.image

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ActivityImageBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper
import kr.co.soogong.master.utility.extension.dp
import timber.log.Timber

@AndroidEntryPoint
class ImageActivity : BaseActivity<ActivityImageBinding>(
    R.layout.activity_image
) {
    private val startPosition: Int by lazy {
        ImageViewActivityHelper.getImagePosition(intent)
    }

    private val images: List<AttachmentDto> by lazy {
        ImageViewActivityHelper.getImages(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            lifecycleOwner = this@ImageActivity

            closeButton.setOnClickListener {
                super.onBackPressed()
            }

            with(sliderViewPager) {
                offscreenPageLimit = 1
                adapter = ImageSliderAdapter()
                registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Timber.tag(TAG).i("onPageSelected: $position")
                        setCurrentIndicator(position)
                    }
                })
                if (!images.isNullOrEmpty()) {
                    setList(images)
                    initIndicators(images.size)
                    // 아래 postDelayed 를 사용한 이유는, TouchImageview library 사용하다보니, ViewPager2에 모든 recyclerview Items 를 만들고,
                    // 초기값으로 0번째 item 을 셋하는 문제가 있어서, 약 0.5초 후에 선택된 사진을 보여주기 위함.
                    postDelayed({
                        Timber.tag(TAG).i("setCurrentItem: $startPosition")
                        setCurrentItem(startPosition, false)
                        visibility = View.VISIBLE
                    }, 500)
                }
            }
        }
    }

    // 인디케이터 init
    private fun initIndicators(count: Int) {
        Timber.tag(TAG).d("initIndicators: $count")
        val indicators = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(2.dp, 8.dp, 2.dp, 8.dp)

        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_indicator_inactive
                )
            )
            indicators[i]?.layoutParams = params
            binding.layoutIndicators.addView(indicators[i])
        }
    }

    // 인디케이터에 변화주는 코드. 선택된 것을 활성화, 선택되지 않은 것들을 비활성화한다.
    fun setCurrentIndicator(position: Int) {
        Timber.tag(TAG).d("setCurrentIndicator: $position")
        val childCount: Int = binding.layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                    )
                )
            }
        }
    }

    companion object {
        private const val TAG = "ImageActivity"
    }
}