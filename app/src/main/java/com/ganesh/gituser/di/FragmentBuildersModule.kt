package com.ganesh.gituser.di


import com.ganesh.gituser.view.UserDetailsFragment
import com.ganesh.gituser.view.UserSearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): UserSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserDetailsFragment


}
