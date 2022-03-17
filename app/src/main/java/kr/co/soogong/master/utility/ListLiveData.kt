package kr.co.soogong.master.utility

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<MutableList<T>>() {
    private val temp = mutableListOf<T>()

    init {
        value = temp
    }

    fun add(item: T) {
        temp.add(item)
        value = temp
    }

    fun addAll(items: List<T>) {
        temp.addAll(items)
        value = temp
    }

    fun replaceAll(items: List<T>) {
        temp.clear()
        temp.addAll(items)
        value = temp
    }

    fun addAllAsSet(items: List<T>, distinct: (List<T>) -> List<T>) {
        addAll(items)
        replaceAll(distinct(temp))
    }

    fun remove(item: T) {
        if (temp.remove(item)) value = temp
    }

    fun removeAt(position: Int) {
        if (temp.getOrNull(position) != null) {
            temp.removeAt(position)
            value = temp
        }
    }

    fun clear() {
        temp.clear()
        value = temp
    }

    fun getItemCount(): Int {
        return value?.count() ?: 0
    }

    companion object
}