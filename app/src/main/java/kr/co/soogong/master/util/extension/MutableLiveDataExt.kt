@file:JvmName("MutableLiveDataExt")

package kr.co.soogong.master.util.extension;

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.mutation(actions: MutableLiveData<T>.() -> Unit){
    actions(this)
    this.value = this.value
}