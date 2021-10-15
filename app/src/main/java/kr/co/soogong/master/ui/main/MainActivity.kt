package kr.co.soogong.master.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.BuildConfig
import kr.co.soogong.master.R
import kr.co.soogong.master.SoogongMasterMessagingService.Companion.initNotificationChannel
import kr.co.soogong.master.SoogongMasterMessagingService.Companion.removeBrokenChannel
import kr.co.soogong.master.data.dto.auth.VersionDto
import kr.co.soogong.master.databinding.ActivityMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.ui.main.MainViewModel.Companion.GET_VERSION_SUCCESSFULLY
import kr.co.soogong.master.uihelper.main.MainBadge
import kr.co.soogong.master.utility.EventObserver
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
), MainBadge {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        viewModel.checkLatestVersion()
        initLayout()
        registerEventObserve()
        registerFCM()
        removeBrokenChannel(this)
        initNotificationChannel(this)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMasterSimpleInfo()
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

    private fun registerEventObserve() {
        viewModel.event.observe(this, EventObserver { (event, value) ->
            when (event) {
                GET_VERSION_SUCCESSFULLY -> {
                    (value as VersionDto).let { versionDto ->
                        this.packageManager.getPackageInfo(this.packageName, 0).versionName.let { currentVersion ->
                            Timber.tag(TAG).d("Version current / latest : $currentVersion / ${versionDto.version}")

                            if (currentVersion != versionDto.version) {
                                CustomDialog.newInstance(
                                    dialogData = if (versionDto.mandatoryYn) DialogData.getUpdatingAppMandatory(this)
                                                else DialogData.getUpdatingAppRecommended(this),
                                    yesClick = {
                                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                                            data = Uri.parse("https://play.google.com/store/search?q=%EC%88%98%EA%B3%B5")
                                            setPackage("com.android.vending")
                                        })
                                    },
                                    noClick = { }
                                ).run {
                                    if (versionDto.mandatoryYn) isCancelable = false
                                    show(supportFragmentManager, tag)
                                }
                            }
                        }
                    }
                }
            }
        })
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