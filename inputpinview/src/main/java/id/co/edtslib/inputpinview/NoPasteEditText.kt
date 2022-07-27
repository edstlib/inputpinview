package id.co.edtslib.inputpinview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText

class NoPasteEditText : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        isLongClickable  = false
        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?) = false

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?) = false
            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?) = false
            override fun onDestroyActionMode(p0: ActionMode?) {
            }

        }
    }

    override fun getSelectionStart(): Int {
        for (element in Thread.currentThread().stackTrace) {
            if (element.methodName == "canPaste") {
                return -1
            }
        }
        return super.getSelectionStart()
    }

    override fun isSuggestionsEnabled() = false

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        try {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
        catch (e: SecurityException) {
            // nothing to do
        }
    }
}