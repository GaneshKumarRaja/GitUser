package com.ganesh.gituser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ganesh.gituser.R
import com.ganesh.gituser.binding.SearchButtonCallback
import com.ganesh.gituser.databinding.UserSearchFragmentBinding
import com.ganesh.gituser.binding.FragmentDataBindingComponent
import com.ganesh.gituser.di.Injectable
import com.ganesh.gituser.view.base.BaseFragment
import com.ganesh.gituser.view_model.UserSearchViewModel
import javax.inject.Inject


class UserSearchFragment : BaseFragment(), Injectable, SearchButtonCallback {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserSearchViewModel

    private lateinit var binding: UserSearchFragmentBinding

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<UserSearchFragmentBinding>(
            inflater,
            R.layout.user_search_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        binding.callback = this

        return dataBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(UserSearchViewModel::class.java)

        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.errorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                showMessage(it)
            }
        })
    }

    override fun searchButtonClicked() {

        if (viewModel.doValidation(binding.edtxtSearch.text.toString().trim())) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as ActivityCallback).loadDetailsFragment()
            }
        }


    }


}