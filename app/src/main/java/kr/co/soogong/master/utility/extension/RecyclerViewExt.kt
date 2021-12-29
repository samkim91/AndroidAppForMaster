@file:JvmName("RecyclerViewExt")

package kr.co.soogong.master.utility.extension;

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.profile.CategoryDto
import kr.co.soogong.master.data.dto.profile.ProjectDto
import kr.co.soogong.master.data.model.common.EndlessScrollableViewModel
import kr.co.soogong.master.ui.major.category.CategoryAdapter
import kr.co.soogong.master.ui.major.project.ProjectAdapter
import kr.co.soogong.master.utility.SpaceItemDecoration

@BindingAdapter(value = ["setStartSpace", "setTopSpace", "setEndSpace", "setBottomSpace"],
    requireAll = false)
fun RecyclerView.setItemSpace(startSpace: Int?, topSpace: Int?, endSpace: Int?, bottomSpace: Int?) {
    this.addItemDecoration(SpaceItemDecoration(startSpace, topSpace, endSpace, bottomSpace))
}

@BindingAdapter("setDivider")
fun RecyclerView.setDivider(drawable: Drawable) {
    val dividerItemDecoration =
        DividerItemDecoration(context, LinearLayoutManager(context).orientation)
    dividerItemDecoration.setDrawable(drawable)

    this.addItemDecoration(dividerItemDecoration)
}


@BindingAdapter("categories")
fun RecyclerView.setCategories(items: List<CategoryDto>?) {
    (adapter as? CategoryAdapter)?.submitList(items)
}

@BindingAdapter("projects")
fun RecyclerView.setProjects(items: List<ProjectDto>?) {
    (adapter as? ProjectAdapter)?.submitList(items)
}

@BindingAdapter("onScroll")
fun RecyclerView.setOnScroll(viewModel: EndlessScrollableViewModel) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            viewModel.onScroll(recyclerView.layoutManager as LinearLayoutManager)
        }
    })
}
