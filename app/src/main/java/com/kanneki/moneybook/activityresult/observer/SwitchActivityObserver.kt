package com.kanneki.moneybook.activityresult.observer

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class SwitchActivityObserver<T, P>(
        private val registry: ActivityResultRegistry,
        private val contract: ActivityResultContract<T, P>,
        private val onResult: (P) -> Unit
): DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<T>

    override fun onCreate(owner: LifecycleOwner) {

        getContent = registry.register(
                "keyResult",
                owner,
                contract,
                ActivityResultCallback(onResult)
        )
    }

    fun startActivity(data: T) {
        getContent.launch(data)
    }

    fun startActivity(data: T, option: ActivityOptionsCompat){
        getContent.launch(data, option)
    }

}