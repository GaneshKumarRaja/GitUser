package com.ganesh.gituser.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ganesh.gituser.R
import com.ganesh.gituser.view.base.BaseActivity
import com.ganesh.gituser.view_model.UserSearchViewModel
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector,
    ActivityCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserSearchViewModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // viewModelObserver()
        setupViewModel()

        if (savedInstanceState == null) {
            loadListFragment()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserSearchViewModel::class.java)
    }

    override fun loadListFragment() {
        addFragment(
            R.id.frm_fragment_container,
            UserSearchFragment(),
            "List"
        )
    }

    override fun loadDetailsFragment() {
        this.replaceFragment(
            R.id.frm_fragment_container,
            UserDetailsFragment(),
            "Details", "back"
        )
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}


interface ActivityCallback {
    fun loadListFragment()
    fun loadDetailsFragment()

}