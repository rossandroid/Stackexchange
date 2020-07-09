package com.rossellamorgante.stackexchange.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rossellamorgante.stackexchange.dependencyinj.DaggerApiComponent
import com.rossellamorgante.stackexchange.model.StackexchangeService
import com.rossellamorgante.stackexchange.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel() {

    @Inject
    lateinit var userService:StackexchangeService

    private val disposable = CompositeDisposable()
    val users = MutableLiveData<List<User>>()
    val userLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init{
        DaggerApiComponent.create().inject(this)
    }

    fun refresh(name:String) {
        fetchUsers(name)
    }

    private fun fetchUsers(name:String) {
        loading.value = true
        disposable.add(
            userService.getUsers(name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<User>>(){
                    override fun onSuccess(value: List<User>) {
                        users.value = value
                        userLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        userLoadError.value = true
                        loading.value = false
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}