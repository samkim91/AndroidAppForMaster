package kr.co.soogong.master.ui.requirements.received.estimate

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.ParseException


class NumberTextWatcher(private val et: EditText) : TextWatcher {
    private var df: DecimalFormat = DecimalFormat("#,###.##").apply {
        isDecimalSeparatorAlwaysShown = true
    }
    private var dfnd: DecimalFormat = DecimalFormat("#,###")
    private var hasFractionalPart = false

    override fun afterTextChanged(s: Editable) {
        et.removeTextChangedListener(this)
        try {
            val iniLen: Int = et.text.length
            val v: String = s.toString().replace(
                java.lang.String.valueOf(
                    df.decimalFormatSymbols.groupingSeparator
                ), ""
            )
            val n: Number? = df.parse(v)
            val cp = et.selectionStart
            if (hasFractionalPart) {
                et.setText(df.format(n))
            } else {
                et.setText(dfnd.format(n))
            }
            val endLen: Int = et.text.length
            val sel = cp + (endLen - iniLen)
            if (sel > 0 && sel <= et.text.length) {
                et.setSelection(sel)
            } else {
                // place cursor at the end?
                et.setSelection(et.text.length - 1)
            }
        } catch (nfe: NumberFormatException) {
            // do nothing?
        } catch (e: ParseException) {
            // do nothing?
        }

        et.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasFractionalPart = s.toString()
            .contains(java.lang.String.valueOf(df.decimalFormatSymbols.decimalSeparator))
    }

    companion object {
        private val TAG = "NumberTextWatcher"
    }
}