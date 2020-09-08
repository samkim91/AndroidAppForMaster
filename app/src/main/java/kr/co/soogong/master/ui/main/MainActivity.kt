package kr.co.soogong.master.ui.main

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMainBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    private val unselectedIcon = arrayOf(
        R.drawable.ic_requirement,
        R.drawable.ic_material,
        R.drawable.ic_profile,
        R.drawable.ic_settings
    )

    private val selectedIcon = arrayOf(
        R.drawable.ic_requirement_choice,
        R.drawable.ic_material_choice,
        R.drawable.ic_profile_choice,
        R.drawable.ic_settings_choice
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { tasks ->
            if (!tasks.isSuccessful) {
                Timber.tag(TAG).e(tasks.exception, "onComplete: getInstanceId failed")
                return@addOnCompleteListener
            }
            val token = tasks.result?.token
            Timber.tag(TAG).d("OnCompleteListener: $token")

            // If you want to send messages to this application instance or
            // manage this apps subscriptions on the server side, send the
            // Instance ID token to your app server.
//            sendRegistrationToServer(token);
        }
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            with(mainTabs) {
                addTab(newTab().setText("받은 요청").setIcon(R.drawable.ic_requirement_choice))
                addTab(newTab().setText("자재 발주").setIcon(R.drawable.ic_material))
                addTab(newTab().setText("프로필").setIcon(R.drawable.ic_profile))
                addTab(newTab().setText("설정").setIcon(R.drawable.ic_settings))

                tabGravity = TabLayout.GRAVITY_FILL

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab) = Unit

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                        val position = tab.position
                        tab.icon = ContextCompat.getDrawable(context, unselectedIcon[position])
                    }

                    override fun onTabSelected(tab: TabLayout.Tab) {
                        val position = tab.position
                        tab.icon = ContextCompat.getDrawable(context, selectedIcon[position])
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