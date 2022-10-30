package zs.xmx.mycontrols.expand_textview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ctetin.expandabletextviewlibrary.R


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:   自定义TextView实现文本超过限定行数显示 ..展开  todo
 *
 */
class ExpandTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    //默认显示最多的行数
    private val DEF_MAX_LINE = 4
    private var TEXT_CONTRACT = "收起"
    private var TEXT_EXPEND = "展开"

    init {
        if (attrs != null) {
            val a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ExpandableTextView,
                defStyleAttr, 0
            )
        }
    }

}