package kr.co.soogong.master.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import kr.co.soogong.master.ui.dialog.loading.LoadingDialog

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    protected val loading: LoadingDialog by lazy {
        LoadingDialog.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layout)
    }

    protected fun bind(action: B.() -> Unit) {
        binding.run(action)
    }

    protected abstract fun initLayout()

    protected fun <T : ViewDataBinding> bind(binding: T, action: T.() -> Unit) {
        binding.run(action)
    }

    protected fun showLoading(fragmentManager: FragmentManager) {
        loading.show(fragmentManager, loading.tag)
    }

    protected fun dismissLoading() {
        loading.dismiss()
    }
}