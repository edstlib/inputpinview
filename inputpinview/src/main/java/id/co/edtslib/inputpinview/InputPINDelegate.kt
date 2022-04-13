package id.co.edtslib.inputpinview

import android.view.View

interface InputPINDelegate {
    fun onCompleted(pin: String)
    fun onTextChanged(text: String)
}