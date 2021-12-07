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

    fun addAllToSet(items: List<T>) {
        temp.addAll(items)
        temp.toSet()
        value = temp
    }

    fun addToSet(item: T) {
        temp.add(item)
        temp.toSet()
        value = temp
    }

    fun remove(item: T) {
        temp.remove(item)
        value = temp
    }

    fun removeAt(position: Int) {
        temp.removeAt(position)
        value = temp
    }

    fun clear() {
        temp.clear()
        value = temp
    }

    fun getItemCount(): Int {
        return value?.count() ?: 0
    }
}