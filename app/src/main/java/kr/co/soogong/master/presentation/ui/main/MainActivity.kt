package kr.co.soogong.master.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.FCMService.Companion.initNotificationChannel
import kr.co.soogong.master.FCMService.Companion.removeBrokenChannel
import kr.co.soogong.master.databinding.ActivityMainBinding
import kr.co.soogong.master.databinding.ViewMainActivityTabItemBinding
import kr.co.soogong.master.domain.entity.common.CodeTable
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DefaultDialog
import kr.co.soogong.master.presentation.ui.common.dialog.popup.DialogData
import kr.co.soogong.master.presentation.ui.profile.ProfileViewModel
import kr.co.soogong.master.presentation.uihelper.main.MainBadge
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
), MainBadge {

    private val viewModel: MainViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserver()
        registerDynamicLinkListener()
        removeBrokenChannel(this)
        initNotificationChannel(this)

        // 최초 앱 실행 시 프로필 업데이트 확인 차 요청
        profileViewModel.requestProfile()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@MainActivity

            with(mainViewPager) {
                isUserInputEnabled = false
                adapter = MainPagerAdapter(this@MainActivity)
                TabLayoutMediator(mainTabs, this) { tab, position ->
                    ViewMainActivityTabItemBinding
                        .inflate(LayoutInflater.from(this@MainActivity), null, false)
                        .apply {
                            icon = TAB_ICONS_MAIN_NAVIGATION[position]
                            label = TAB_TEXTS_MAIN_NAVIGATION[position]
                        }.run {
                            tab.customView = this@run.root
                        }
                }.attach()
            }
        }
    }

    private fun registerEventObserver() {
        viewModel.selectedMainTabInMainActivity.observe(this) { position ->
            binding.mainTabs.getTabAt(position)?.select()
        }

        profileViewModel.profile.observe(this) { profile ->
            when {
                profile?.approvedStatus == CodeTable.NOT_APPROVED -> moveToProfileFragment(true)
                profile?.basicInformation?.portfolioCount == 0 || profile?.basicInformation?.priceByProjectCount == 0 -> moveToProfileFragment(
                    false)
            }
        }
    }

    // 인앱 메시지로 다이나믹링크를 받았을 때, 어떻게 처리할지 결정
    private fun registerDynamicLinkListener() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                    // link 쿼리에 value 가 대상 화면으로 날아옴
                    when (pendingDynamicLinkData.link?.getQueryParameter("link")) {
                        "noticeFragment" -> viewModel.selectedMainTabInMainActivity.value =
                            TAB_TEXTS_MAIN_NAVIGATION.indexOf(R.string.main_activity_navigation_bar_preferences)
                    }
                }
            }
            .addOnFailureListener {
                Timber.tag(TAG).w("registerDynamicLinkListener onFailure: $it")
            }
    }

    private fun moveToProfileFragment(required: Boolean) {
        DefaultDialog.newInstance(
            if (required) DialogData.getAskingFillRequiredProfile() else DialogData.getAskingFillBasicProfile()
        ).let { dialog ->
            dialog.setButtonsClickListener(
                onPositive = {
                    viewModel.selectedMainTabInMainActivity.value =
                        TAB_TEXTS_MAIN_NAVIGATION.indexOf(R.string.main_activity_navigation_bar_profile)
                },
                onNegative = { }
            )
            dialog.show(supportFragmentManager, dialog.tag)
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