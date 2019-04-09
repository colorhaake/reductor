package javatest.colorhaake.com.example3.view.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import javatest.colorhaake.com.example3.view.utils.OnCompoundDrawableTouchListener.Companion.LEFT
import javatest.colorhaake.com.example3.view.utils.OnCompoundDrawableTouchListener.Companion.RIGHT
import java.util.*

class ClearableEditText : AppCompatEditText, TextWatcher, OnCompoundDrawableTouchListener {

    private var mClearDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int)
            : super(context, attrs, defStyle) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        setOnTouchListener(this)
        addTextChangedListener(this)
        attrs?.let { setupAttributes(it) }
    }

    private fun setupAttributes(attrs: AttributeSet) {
        val map = mapOf(
                android.R.attr.drawableEnd to
                        fun (a: TypedArray, i: Int) =
                                setClearButtonImageResource(a.getResourceId(i, 0))
        )

        ComponentHelper.setupAttrs(context, attrs, map)
    }

    fun setClearButtonImageResource(@DrawableRes resId: Int) {
        mClearDrawable = ContextCompat.getDrawable(context, resId)
        setClearButtonVisible(false)
    }

    private fun setClearButtonVisible(visible: Boolean) {
        mClearDrawable?.setVisible(visible, false)

        setCompoundDrawablesRelativeWithIntrinsicBounds(
                compoundDrawablesRelative[0],
                compoundDrawablesRelative[1],
                mClearDrawable.takeIf { visible },
                compoundDrawablesRelative[3]
        )
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        setClearButtonVisible(text?.isNotEmpty() == true)
    }

    override fun onDrawableTouch(
            v: TextView,
            drawableIndex: Int,
            drawableBounds: Rect,
            event: MotionEvent
    ): Boolean {
        val isLeftToRight = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) ==
                ViewCompat.LAYOUT_DIRECTION_LTR
        val endIndex = if (isLeftToRight) RIGHT else LEFT

        if (drawableIndex != endIndex
                || v.compoundDrawables[endIndex]?.isVisible != true
                || event.action != MotionEvent.ACTION_UP
        ) {
            return false
        }

        setText("")
        return true
    }
}