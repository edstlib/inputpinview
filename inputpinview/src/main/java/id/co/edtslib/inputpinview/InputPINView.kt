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
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.addTextChangedListener

class InputPINView : LinearLayoutCompat {
    private var editText: AppCompatEditText? = null
    private var textColorResId = 0
    private var shapeResId = 0
    private var length = 4
    private var showPassword = false // false maka muncul *

    enum class PinType {
        Number, Password, PasswordWithEye
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
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

            shapeResId = a.getResourceId(R.styleable.InputPINView_pinShape, 0)
            length = a.getInteger(R.styleable.InputPINView_pinLength, 4)
            val textStyleReId = a.getResourceId(R.styleable.InputPINView_pinTextStyle, 0)
            val width = a.getDimension(
                R.styleable.InputPINView_pinWidth,
                resources.getDimensionPixelSize(R.dimen.pin_dimen_40dp).toFloat()
            )
            val height = a.getDimension(
                R.styleable.InputPINView_pinHeight,
                resources.getDimensionPixelSize(R.dimen.pin_dimen_50dp).toFloat()
            )
            textColorResId = a.getColor(R.styleable.InputPINView_pinTextColor, 0)
            val margin = a.getDimension(
                R.styleable.InputPINView_pinMargin,
                resources.getDimensionPixelSize(R.dimen.pin_dimen_8dp).toFloat()
            )
            val pinType = a.getInteger(R.styleable.InputPINView_pinType, 0)
            val pinEyePassword = a.getResourceId(R.styleable.InputPINView_pinEyePassword,
                R.drawable.img_password_eye)

            editText = AppCompatEditText(context)
            editText?.isFocusableInTouchMode = true
            editText?.inputType = InputType.TYPE_CLASS_NUMBER
            editText?.gravity = Gravity.CENTER
            editText?.isSingleLine = true
            editText?.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
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
                textView.isSelected = it == 0

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
                        if (idx < length) {
                            val textView = getChildAt(idx + 1) as AppCompatTextView
                            textView.text = if (idx < it.length) {
                                when (pinType) {
                                    PinType.Password.ordinal -> "*"
                                    PinType.PasswordWithEye.ordinal ->
                                        if (showPassword) {
                                            it[idx].toString()
                                        } else {
                                            "*"
                                        }

                                    else -> it[idx].toString()
                                }
                            } else ""
                            textView.isSelected = idx == it.length
                            if (idx == it.length) {
                                if (textColorResId != 0) {
                                    textView.setTextColor(textColorResId)
                                }
                                if (shapeResId != 0) {
                                    textView.setBackgroundResource(shapeResId)
                                }
                            }
                        }
                    }

                    if (it.length >= length) {
                        delegate?.onCompleted(it.toString())
                    }

                    delegate?.onTextChanged(it.toString())
                }
            }

            if (pinType == PinType.PasswordWithEye.ordinal) {
                val imageView = AppCompatImageView(context)
                addView(imageView)

                imageView.setImageResource(pinEyePassword)

                val layoutParams = imageView.layoutParams as LinearLayout.LayoutParams
                layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
                layoutParams.marginStart = margin.toInt()

                imageView.setOnClickListener {
                    showPassword = ! showPassword
                    imageView.isSelected = showPassword

                    redraw(showPassword)
                }
            }

            a.recycle()

            postDelayed({
                showKeyboard()
            }, 250)
        }
    }

    fun redraw(showPassword: Boolean) {
        repeat(length) { idx ->
            val s = editText?.text?.toString()

            if (s != null) {
                val textView = getChildAt(idx + 1) as AppCompatTextView
                val cc = if (idx < s.length) s[idx].toString() else ""
                if (cc.isNotEmpty()) {
                    textView.text = if (showPassword) s[idx].toString() else "*"
                }
                else {
                    textView.text = ""
                }


            }
        }
    }

    fun clear() {
        editText?.setText("")

        for (i in 1 until length+1) {
            val textView = getChildAt(i) as AppCompatTextView
            if (textColorResId != 0) {
                textView.setTextColor(textColorResId)
            }
            if (shapeResId != 0) {
                textView.setBackgroundResource(shapeResId)
            }
        }
    }

    private fun setInitialColor() {
        if (textColorResId != 0) {
            for (i in 1 until length+1) {
                val textView = getChildAt(i) as AppCompatTextView
                textView.setTextColor(textColorResId)
            }
        }
    }

    private fun showKeyboard() {
        editText?.requestFocus()

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun setTextColor(color: Int) {
        for (i in 1 until length+1) {
            val textView = getChildAt(i) as AppCompatTextView
            textView.setTextColor(ContextCompat.getColor(context, color))
        }
    }

    fun setShapeBackgroundResource(resId: Int) {
        for (i in 1 until length+1) {
            val textView = getChildAt(i) as AppCompatTextView
            textView.setBackgroundResource(resId)
        }
    }


}