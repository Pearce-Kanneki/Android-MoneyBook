package com.kanneki.moneybook.activityresult.observer

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class RequestPermissionObserver(
        private val registry: ActivityResultRegistry,
        private val onResult: (Boolean) -> Unit
): DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner){

        getContent = registry.register(
                "key",
                owner,
                ActivityResultContracts.RequestPermission()
        ){result ->

            onResult(result)
        }
    }


    fun requestPermissionCamera(){

        getContent.launch(android.Manifest.permission.CAMERA)
    }
}