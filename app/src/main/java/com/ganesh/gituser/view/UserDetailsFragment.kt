package com.ganesh.gituser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ganesh.gituser.R
import com.ganesh.gituser.databinding.UserDetailsFragmentBinding
import com.ganesh.gituser.binding.FragmentDataBindingComponent
import com.ganesh.gituser.di.Injectable

import com.ganesh.gituser.view_model.UserSearchViewModel
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.widget.TextView
import com.ganesh.gituser.binding.TextViewCallback
import com.ganesh.gituser.view.base.BaseFragment


class UserDetailsFragment : BaseFragment(), Injectable, TextViewCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserSearchViewModel

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var binding: UserDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val dataBinding = DataBindingUtil.inflate<UserDetailsFragmentBinding>(
            inflater,
            R.layout.user_details_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        binding.textViewCallback = this
        return dataBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearResult()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(UserSearchViewModel::class.java)

        setupObserver()
        // get selected item details
        viewModel.getUserDetails()

    }


    private fun setupObserver() {
        viewModel.userModelLiveData.observe(this, Observer {
            binding.userDetails = it
        })
    }

    override fun callBrowser(view: View) {
        val txtView = view as TextView
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(txtView.text.toString()))
        startActivity(intent)
    }


}