package id.co.edtslib.inputpinview

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener

class InputPINView: LinearLayoutCompat {
    private var editText: AppCompatEditText? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init (attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    var delegate: InputPINDelegate? = null

    @SuppressLint("ClickableViewAccessibility")
    private fun init(attrs: AttributeSet?) {
        orientation = HORIZONTAL

        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.InputPINView,
                0, 0
            )

            val shapeResId = a.getResourceId(R.styleable.InputPINView_pinShape, 0)
            val length = a.getInteger(R.styleable.InputPINView_pinLength, 4)
            val textStyleReId = a.getResourceId(R.styleable.InputPINView_pinTextStyle, 0)
            val width = a.getDimension(R.styleable.InputPINView_pinWidth,
                resources.getDimensionPixelSize(R.dimen.dimen_40dp).toFloat())
            val height = a.getDimension(R.styleable.InputPINView_pinHeight,
                resources.getDimensionPixelSize(R.dimen.dimen_50dp).toFloat())
            val textColorResId = a.getColor(R.styleable.InputPINView_pinTextColor, 0)
            val margin = a.getDimension(R.styleable.InputPINView_pinMargin,
                resources.getDimensionPixelSize(R.dimen.dimen_8dp).toFloat())


            editText = AppCompatEditText(context)
            editText?.isFocusableInTouchMode = true
            editText?.inputType = InputType.TYPE_CLASS_NUMBER
            editText?.isSingleLine = true
            editText?.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            editText?.isCursorVisible = false
            editText?.imeOptions = EditorInfo.IME_ACTION_DONE

            editText?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(length))

            addView(editText)

            val layoutParamsEditText = editText?.layoutParams as LayoutParams
            layoutParamsEditText.width = 1//width.toInt()
            layoutParamsEditText.height = 1//height.toInt()

            repeat(length) {
                val textView = AppCompatTextView(context)
                textView.gravity = Gravity.CENTER
                addView(textView)

                textView.setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        showKeyboard()
                    }

                    false
                }

                val layoutParams = textView.layoutParams as LayoutParams
                layoutParams.width = width.toInt()
                layoutParams.height = height.toInt()
                if (it > 0) {
                    layoutParams.leftMargin = margin.toInt()
                }

                if (shapeResId != 0) {
                    textView.setBackgroundResource(shapeResId)
                }

                if (textStyleReId != 0) {
                    TextViewCompat.setTextAppearance(textView, textStyleReId)
                }

                if (textColorResId != 0) {
                    textView.setTextColor(textColorResId)
                }
            }

            editText?.addTextChangedListener {
                if (it != null) {
                    repeat(length) { idx ->
                        if (idx + 1 < childCount) {
                            val textView = getChildAt(idx + 1) as AppCompatTextView
                            textView.text = if (idx < it.length) it[idx].toString() else ""
                        }
                    }

                    if (it.length >= length) {
                        delegate?.onCompleted(it.toString())
                    }
                }
            }

            a.recycle()

            postDelayed( {
                showKeyboard()
            }, 250)
        }
    }

    private fun showKeyboard() {
        editText?.requestFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }


}