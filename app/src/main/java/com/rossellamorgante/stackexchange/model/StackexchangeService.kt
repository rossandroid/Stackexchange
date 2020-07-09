package com.rossellamorgante.stackexchange.model

import com.rossellamorgante.stackexchange.dependencyinj.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class StackexchangeService {
    private val order = "asc"
    private val sort = "reputation"
    private val site = "stackoverflow"


    @Inject
    lateinit var api: StackexchangeAPI
    init{
        DaggerApiComponent.create().inject(this)
    }

    fun getUsers(name: String): Single<List<User>> {
        return api.getUsers(order, sort, name, site)
             .map { list -> list.items}
    }
}