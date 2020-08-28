package kr.co.soogong.master.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kr.co.soogong.master.BR

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : Fragment() {

    abstract val viewModel: VM

    private lateinit var binding: B
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
    }

    protected fun bind(action: B.() -> Unit) {
        binding.run(action)
    }

    protected fun <T : ViewDataBinding> bind(binding: T, action: T.() -> Unit) {
        binding.run(action)
    }
}