package kr.co.soogong.master.ui.major

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.databinding.ActivityMajorBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.major.category.CategoryFragment
import kr.co.soogong.master.ui.major.project.ProjectFragment
import timber.log.Timber

@AndroidEntryPoint
class MajorActivity : BaseActivity<ActivityMajorBinding>(
    R.layout.activity_major
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        bind {
            abHeader.setButtonBackClickListener { onBackPressed() }
            setCategoryFragment()
        }
    }

    private fun setCategoryFragment() {
        with(binding.abHeader) {
            title = getString(R.string.selecting_project)
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.fcvContainer.id, CategoryFragment.newInstance())
            .commit()
    }

    fun moveToProjectFragment(category: CategoryDto) {
        with(binding.abHeader) {
            title = category.name
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                R.anim.slide_in_from_left, R.anim.slide_out_to_right
            )
            .replace(binding.fcvContainer.id, ProjectFragment.newInstance(category.id!!))
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()       // ProjectFragment 에서는 CategoryFragment 가 나오게 함
            setCategoryFragment()
        } else {
            super.onBackPressed()       // CategoryFragment 에서는 Activity 를 종료
        }
    }

    companion object {
        private const val TAG = "MajorActivity"

    }
}