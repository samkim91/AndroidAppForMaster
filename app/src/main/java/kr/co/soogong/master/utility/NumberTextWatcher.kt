package kr.co.soogong.master.utility

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.ParseException

class NumberTextWatcher(private val editText: EditText) : TextWatcher {
    private var decimalFormat1: DecimalFormat = DecimalFormat("#,###.##").apply {
        isDecimalSeparatorAlwaysShown = true
    }
    private var decimalFormat: DecimalFormat = DecimalFormat("#,###")
    private var hasFractionalPart = false

    override fun afterTextChanged(s: Editable) {
        editText.removeTextChangedListener(this)
        try {
            val iniLen: Int = editText.text.length
            val v: String = s.toString().replace(
                java.lang.String.valueOf(
                    decimalFormat1.decimalFormatSymbols.groupingSeparator
                ), ""
            )
            val n: Number? = decimalFormat1.parse(v)
            val cp = editText.selectionStart
            if (hasFractionalPart) {
                editText.setText(decimalFormat1.format(n))
            } else {
                editText.setText(decimalFormat.format(n))
            }
            val endLen: Int = editText.text.length
            val sel = cp + (endLen - iniLen)
            if (sel > 0 && sel <= editText.text.length) {
                editText.setSelection(sel)
            } else {
                // place cursor at the end?
                editText.setSelection(editText.text.length - 1)
            }
        } catch (nfe: NumberFormatException) {
            // do nothing?
        } catch (e: ParseException) {
            // do nothing?
        }

        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasFractionalPart = s.toString()
            .contains(java.lang.String.valueOf(decimalFormat1.decimalFormatSymbols.decimalSeparator))
    }

    companion object {
        private val TAG = "NumberTextWatcher"
    }
}