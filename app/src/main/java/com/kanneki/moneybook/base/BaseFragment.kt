package com.kanneki.moneybook.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kanneki.moneybook.ui.main.MainViewModel

abstract class BaseFragment<VT: ViewModel, VB: ViewDataBinding>: Fragment() {

    abstract val viewModel: VT

    lateinit var binding: VB

    protected val mainViewModel: MainViewModel by activityViewModels()

    @LayoutRes
    abstract fun getLayoutRes(): Int

    open fun isActivityBottomNavigationViewShow(): Boolean = false

    open fun initBindingData(){}

    open fun initData() {}

    open fun initView() {}

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutRes(),
                container,
                false
        )
        initBindingData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.setBottomNavigationViewShow(isActivityBottomNavigationViewShow())
        initData()
        initView()
    }

}