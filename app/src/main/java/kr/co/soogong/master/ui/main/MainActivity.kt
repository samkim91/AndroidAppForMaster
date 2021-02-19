package kr.co.soogong.master.ui.main

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
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

    companion object {
        private const val TAG = "MainActivity"
    }
}