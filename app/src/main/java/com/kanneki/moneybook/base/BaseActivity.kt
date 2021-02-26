package com.kanneki.moneybook.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VT: ViewModel, VB: ViewDataBinding>: AppCompatActivity() {

    open val dataBinding: VB by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes())
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract val viewModel: VT

    open fun initDataBinding(){}

    open fun initData(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDataBinding()
        initData()
    }
}