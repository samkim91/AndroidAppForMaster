package kr.co.soogong.master.ui.requirements.progress.detail

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityProgressDetailBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.requirments.progress.detail.ProgressDetailActivityHelper
import timber.log.Timber

class ProgressDetailActivity : BaseActivity<ActivityProgressDetailBinding>(
    R.layout.activity_progress_detail
) {
    private val keycode by lazy {
        intent.getBundleExtra(ProgressDetailActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getString(ProgressDetailActivityHelper.BUNDLE_KEY_RECEIVED_KEY, "") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@ProgressDetailActivity

            with(mainTabs) {
                addTab(newTab().setText("수리요청서"))
                addTab(newTab().setText("내가 쓴 견적서"))

                tabGravity = TabLayout.GRAVITY_FILL

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab) = Unit

                    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        mainViewPager.currentItem = tab.position
                    }
                })
            }

            with(actionBar) {
                title.text = "자세히 보기"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            with(mainViewPager) {
                adapter = ProgressPagerAdapter(supportFragmentManager, mainTabs.tabCount, keycode)

                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainTabs))

                swipEnabled = false

                currentItem = 0
            }
        }
    }

    companion object {
        private const val TAG = "ProgressDetailActivity"
    }
}