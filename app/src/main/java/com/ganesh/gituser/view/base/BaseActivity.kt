package com.ganesh.gituser.view.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.IdRes

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected fun addFragment(
        @IdRes containerViewId: Int,
        @NonNull fragment: Fragment,
        @NonNull fragmentTag: String
    ) {
        supportFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment, fragmentTag)
            .disallowAddToBackStack()
            .commit()
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        @NonNull fragment: Fragment,
        @NonNull fragmentTag: String,
        @Nullable backStackStateName: String
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(backStackStateName)
            .commit()
    }
}