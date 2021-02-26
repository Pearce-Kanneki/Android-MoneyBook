package com.kanneki.moneybook.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kanneki.moneybook.R
import kotlinx.android.synthetic.main.bottomsheets_modify.*

class ModifyBottomSheetDialog(
    private val callback: (Int) -> Unit
):BottomSheetDialogFragment(){

    companion object{

        const val TAG_DIALOG = "dialog"
        const val DIALOG_TYPE_MODIFY = 1
        const val DIALOG_TYPE_DELETE = 2
    }

    private var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.bottomsheets_modify,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply{
            bottomTitle.text = title
            bottomModify.setOnClickListener {v ->

                callback(DIALOG_TYPE_MODIFY)
                dismiss()
            }
            bottomDelete.setOnClickListener {

                callback(DIALOG_TYPE_DELETE)
                dismiss()
            }
        }
    }

    fun setTitle(str: String) {
        title = str
    }

}