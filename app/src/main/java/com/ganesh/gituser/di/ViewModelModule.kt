package com.ganesh.gituser.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ganesh.gituser.view_model.UserSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserSearchViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserSearchViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
