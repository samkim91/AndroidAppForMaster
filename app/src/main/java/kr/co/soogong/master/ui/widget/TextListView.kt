package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewTextListBinding
import kr.co.soogong.master.databinding.ViewholderAddressBinding

class TextListView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding: ViewTextListBinding =
        ViewTextListBinding.inflate(LayoutInflater.from(context), this, true)

    fun setList(list: List<String>?) {
        with(binding.list) {
            adapter = AddressAdapter(list ?: emptyList())
        }
    }

    private class AddressAdapter(
        val list: List<String>
    ) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
        class ViewHolder(
            val binding: ViewholderAddressBinding
        ) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ViewholderAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.address.text = list[position]
        }

        override fun getItemCount() = list.size
    }
}