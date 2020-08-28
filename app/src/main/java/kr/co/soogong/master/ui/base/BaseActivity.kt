package kr.co.soogong.master.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import kr.co.soogong.master.BR

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : AppCompatActivity() {

    abstract val viewModel: VM

    protected lateinit var binding: B
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layout)
        binding.setVariable(BR.vm, viewModel)
    }

    protected fun bind(action: B.() -> Unit) {
        binding.run(action)
    }

    protected fun <T : ViewDataBinding> bind(binding: T, action: T.() -> Unit) {
        binding.run(action)
    }

}