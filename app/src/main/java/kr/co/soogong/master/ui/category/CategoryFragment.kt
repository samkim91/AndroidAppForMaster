package kr.co.soogong.master.ui.category

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.nex3z.togglebuttongroup.button.LabelToggle
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentCategoryBinding
import kr.co.soogong.master.ext.createLabelToggle
import kr.co.soogong.master.ui.base.BaseBottomSheetDialogFragment
import timber.log.Timber

class CategoryFragment(
    private val selectList: List<String>,
    private val listener: (List<String>) -> Unit
) : BaseBottomSheetDialogFragment<FragmentCategoryBinding>(
    R.layout.fragment_category
) {
    private val viewModel: CategoryViewModel by lazy {
        ViewModelProvider(this).get(CategoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    private fun initLayout() {
        bind {
            lifecycleOwner = viewLifecycleOwner

            setCloseClick {
                dismiss()
            }

            setCompleteClick {
                val list: MutableList<String> = ArrayList()
                for (item in category.checkedIds) {
                    list.add(category.findViewById<LabelToggle>(item).text.toString())
                }
                listener(list)
                dismiss()
            }

            viewModel.list.observe(viewLifecycleOwner, { list ->
                if (!list.isNullOrEmpty()) {
                    category.removeAllViews()

                    for (item in list) {
                        val view = createLabelToggle(
                            requireContext(),
                            item,
                            checked = selectList.contains(item),
                            clickable = true
                        )
                        category.addView(view)
                    }
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCategoryList()
    }

    companion object {
        const val TAG = "CategoryFragment"

        fun newInstance(
            list: List<String> = emptyList(),
            listener: (List<String>) -> Unit
        ): CategoryFragment {
            return CategoryFragment(list, listener)
        }
    }
}