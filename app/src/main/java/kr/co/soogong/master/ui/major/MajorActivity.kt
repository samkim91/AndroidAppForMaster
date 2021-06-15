package kr.co.soogong.master.ui.major

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.Category
import kr.co.soogong.master.databinding.ActivityMajorBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.major.category.CategoryFragment
import kr.co.soogong.master.ui.major.project.ProjectFragment
import timber.log.Timber

@AndroidEntryPoint
class MajorActivity : BaseActivity<ActivityMajorBinding>(
    R.layout.activity_major
), MajorFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, CategoryFragment.newInstance())
            .commit()

        setCategorySelectFragmentActionbar()
    }

    private fun setCategorySelectFragmentActionbar() {
        with(binding.actionBar) {
            title.text = "업종 및 프로젝트 선택"
            backButton.setOnClickListener {
                super.onBackPressed()
            }
        }
    }

    override fun setProjectFragment(category: Category) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                R.anim.slide_in_from_left, R.anim.slide_out_to_right
            )
            .replace(R.id.fragment, ProjectFragment.newInstance(category))
            .addToBackStack(null)
            .commit()

        with(binding.actionBar) {
            title.text = category.name
            backButton.setOnClickListener {
                supportFragmentManager.popBackStack()
                setCategorySelectFragmentActionbar()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            setCategorySelectFragmentActionbar()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "MajorActivity"
    }
}