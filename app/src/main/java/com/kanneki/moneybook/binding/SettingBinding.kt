package com.kanneki.moneybook.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.kanneki.moneybook.model.TitleActivityModule.Companion.TYPE_SWITCH

@BindingAdapter("settingSwitchShow")
fun BindAdapterSettingSwitchShow(view: View, type: Int){

    view.visibility =
            if (type == TYPE_SWITCH) View.VISIBLE else View.GONE
}

@BindingAdapter("settingShowImg")
fun BindingAdapterSettingShowImg(view: View, type: Int){

    view.visibility =
            if(type == TYPE_SWITCH) View.GONE else View.VISIBLE
}