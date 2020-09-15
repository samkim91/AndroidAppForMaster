package kr.co.soogong.master.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : Fragment() {

    protected lateinit var binding: B
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    protected fun bind(action: B.() -> Unit) {
        binding.run(action)
    }

    protected abstract fun initLayout()

    protected fun <T : ViewDataBinding> bind(binding: T, action: T.() -> Unit) {
        binding.run(action)
    }
}