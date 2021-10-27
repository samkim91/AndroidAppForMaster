package kr.co.soogong.master.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.SoogongMasterMessagingService.Companion.initNotificationChannel
import kr.co.soogong.master.SoogongMasterMessagingService.Companion.removeBrokenChannel
import kr.co.soogong.master.databinding.ActivityMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.main.MainBadge
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
), MainBadge {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerFCM()
        removeBrokenChannel(this)
        initNotificationChannel(this)
    }

    private fun registerFCM() {
        Timber.tag(TAG).d("registerFCM: ")
        viewModel.registerFCM()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@MainActivity

            with(mainViewPager) {
                isUserInputEnabled = false
                adapter = MainPagerAdapter(this@MainActivity)
                TabLayoutMediator(mainTabs, this) { tab, position ->
                    tab.text = getString(TabTextList[position])
                    tab.icon =
                        ResourcesCompat.getDrawable(resources, TabIconList[position], null)
                }.attach()
            }
        }
    }

    override fun setRequirementsBadge(badgeCount: Int) {
        val badge = binding.mainTabs.getTabAt(0)?.orCreateBadge
        badge?.number = badgeCount
    }

    override fun unsetRequirementsBadge() {
        binding.mainTabs.getTabAt(0)?.removeBadge()
    }

    override fun setProfileBadge(badgeCount: Int) {
        val badge = binding.mainTabs.getTabAt(1)?.orCreateBadge
        badge?.number = badgeCount
    }

    override fun unsetProfileBadge() {
        binding.mainTabs.getTabAt(1)?.removeBadge()
    }

    override fun setMyPageBadge(badgeCount: Int) {
        val badge = binding.mainTabs.getTabAt(2)?.orCreateBadge
        badge?.number = badgeCount
    }

    override fun unsetMyPageBadge() {
        binding.mainTabs.getTabAt(2)?.removeBadge()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}