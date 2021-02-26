package com.kanneki.moneybook.ui.dialog

import android.app.AlertDialog
import android.view.View
import com.kanneki.moneybook.R
import kotlinx.android.synthetic.main.dialog_datetime.*
import kotlinx.android.synthetic.main.dialog_edittext.*

class UiDialog {
    companion object {

        fun dateDialog(view: View, callback: (String?) -> Unit){
            val dialog = AlertDialog.Builder(view.context)
                .setTitle(R.string.dialog_title_date)
                .setView(R.layout.dialog_datetime)
                .create()

            dialog.setButton(AlertDialog.BUTTON_POSITIVE, view.context.getText(R.string.dialog_ok)){_, _ ->
                dialog.dismiss()
                val timeString = "${dialog.date_picker.year}/" +
                        String.format("%02d",dialog.date_picker.month + 1) +
                        "/${String.format("%02d", dialog.date_picker.dayOfMonth)}"
                callback(timeString)
            }
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, view.context.getText(R.string.dialog_cancel)){_,_ ->
                dialog.dismiss()
                callback(null)
            }

            dialog.show()
        }


        fun selectTypeDialog(view: View, callback: (Boolean?) -> Unit){
            AlertDialog.Builder(view.context)
                    .setTitle(R.string.select_type_dialog)
                    .setItems(R.array.record_title){ d, i ->
                        callback(i == 1)
                    }.show()
        }

        fun selectItemDialog(view: View, list: Array<String>, callback: (Int) -> Unit){
            AlertDialog.Builder(view.context)
                    .setTitle(R.string.dialog_title_record_list_title)
                    .setItems(list){_, index ->
                        callback(index)
                    }.show()
        }

        fun messageMessageDialog(view: View, message: String, callback: (Boolean?) -> Unit){
            AlertDialog.Builder(view.context)
                .setTitle(R.string.dialog_title)
                .setMessage(message)
                .setNegativeButton(R.string.dialog_cancel){ _, _ ->
                    callback(false)
                }
                .setPositiveButton(R.string.dialog_ok){ _, _ ->
                    callback(true)
                }.show()

        }

        fun editTextDialog(
            view: View,
            title: Int = R.string.dialog_title_modify,
            callback: (String?) -> Unit
        ){
            val dialog = AlertDialog.Builder(view.context)
                .setTitle(title)
                .setView(R.layout.dialog_edittext)
                .create()

            dialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                view.context.getString(R.string.dialog_ok)){ _, _ ->


                callback(dialog.dialogEditText.text.toString())
                dialog.dismiss()
            }

            dialog.setButton(
                AlertDialog.BUTTON_NEGATIVE,
                view.context.getString(R.string.dialog_cancel)
            ){ _, _ ->

                dialog.dismiss()
            }

            dialog.show()
        }

    }
}