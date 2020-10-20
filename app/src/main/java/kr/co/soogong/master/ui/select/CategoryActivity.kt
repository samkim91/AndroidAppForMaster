package kr.co.soogong.master.ui.select

import android.os.Bundle
import kr.co.soogong.master.R
import kr.co.soogong.master.data.category.Category
import kr.co.soogong.master.databinding.ActivityCategoryBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.select.category.CategorySelectFragment
import kr.co.soogong.master.ui.select.project.ProjectSelectFragment
import timber.log.Timber

class CategoryActivity : BaseActivity<ActivityCategoryBinding>(
    R.layout.activity_category
), SelectFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        setCategorySelectFragment()
    }

    private fun setCategorySelectFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, CategorySelectFragment.newInstance())
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

    override fun setProjectSelectFragment(category: Category) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_from_right, R.anim.slide_out_to_left,
                R.anim.slide_in_from_left, R.anim.slide_out_to_right
            )
            .replace(R.id.fragment, ProjectSelectFragment.newInstance(category))
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
        private const val TAG = "CategoryActivity"
    }
}