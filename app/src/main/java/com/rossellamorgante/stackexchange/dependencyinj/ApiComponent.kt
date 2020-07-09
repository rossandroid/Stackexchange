package com.rossellamorgante.stackexchange.dependencyinj

import com.rossellamorgante.stackexchange.model.StackexchangeService
import com.rossellamorgante.stackexchange.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: StackexchangeService)

    fun inject(viewModel: ListViewModel)
}