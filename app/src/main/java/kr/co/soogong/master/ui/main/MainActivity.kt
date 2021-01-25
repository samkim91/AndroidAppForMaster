package kr.co.soogong.master.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerFCM()
    }

    private fun registerFCM() {
        viewModel.registerFCM()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@MainActivity

            with(mainTabs) {
                addTab(newTab().setText("받은 요청").setIcon(R.drawable.ic_total_request))
                addTab(newTab().setText("보낸 요청").setIcon(R.drawable.ic_request_received))
                addTab(newTab().setText("마이페이지").setIcon(R.drawable.ic_mypage))

                tabGravity = TabLayout.GRAVITY_FILL

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab) = Unit

                    override fun onTabUnselected(tab: TabLayout.Tab) = Unit

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        mainViewPager.currentItem = tab.position
                    }
                })
            }

            with(mainViewPager) {
                adapter = MainPagerAdapter(supportFragmentManager, mainTabs.tabCount)

                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mainTabs))

                swipEnabled = false

                currentItem = 0
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}