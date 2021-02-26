package com.kanneki.moneybook.ui.main

import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.kanneki.moneybook.R
import com.kanneki.moneybook.base.BaseActivity
import com.kanneki.moneybook.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun initDataBinding() {
        super.initDataBinding()
        with(dataBinding) {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            root.bottomNavigationView.setupWithNavController(
                    findNavController(R.id.mainFragment)
            )
        }
    }

    override fun initData() {
        super.initData()
        GlobalScope.launch {
            viewModel.initType()
        }
    }
}