package zs.xmx.lib_ddmenu

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class DropDownMenu @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    //顶部menu布局
    private var tabMenuView: LinearLayout

    private var menuBackgroundColor: Int = Color.WHITE
    private var underlineColor: Int = Color.parseColor("#cccccc")

    //menu tab 字体大小
    private var menuTextSize = 14

    //menu tab 选中时的字体颜色
    private var menuTextSelectedColor = Color.BLUE

    //menu tab 未选中时的字体颜色
    private var menuTextUnSelectedColor = Color.BLACK

    //menu tab 默认选中时的图标
    private var menuSelectedIcon = 0

    //menu tab 默认未选中时的图标
    private var menuUnselectedIcon = 0

    //底部容器:  顶部menu布局以下的容器布局
    private var containerView: FrameLayout

    //遮罩半透明View，点击可关闭DropDownMenu
    private lateinit var maskView: View

    //遮罩颜色
    private var maskColor = Color.parseColor("#88888888")

    //弹出菜单布局
    private lateinit var popupMenuViews: FrameLayout

    //tabMenuView里面选中的tab位置, -1表示未选中
    private var currentTabPosition = -1


    init {
        orientation = VERTICAL
        //最外层的属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu)
        menuBackgroundColor =
            typedArray.getColor(R.styleable.DropDownMenu_ddMenuBackgroundColor, menuBackgroundColor)
        menuTextSize =
            typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_ddMenuTextSize, menuTextSize)
        menuTextSelectedColor = typedArray.getColor(
            R.styleable.DropDownMenu_ddMenuTextSelectedColor, menuTextSelectedColor
        )
        menuTextUnSelectedColor = typedArray.getColor(
            R.styleable.DropDownMenu_ddMenuTextUnselectedColor, menuTextUnSelectedColor
        )
        menuSelectedIcon =
            typedArray.getResourceId(R.styleable.DropDownMenu_ddMenuSelectedIcon, menuSelectedIcon)
        menuUnselectedIcon = typedArray.getResourceId(
            R.styleable.DropDownMenu_ddMenuUnselectedIcon, menuUnselectedIcon
        )
        maskColor = typedArray.getColor(
            R.styleable.DropDownMenu_ddMaskColor, maskColor
        )
        //释放资源
        typedArray.recycle()

        //初始化tabMenuView
        tabMenuView = LinearLayout(context)
        val params =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tabMenuView.orientation = HORIZONTAL
        tabMenuView.setBackgroundColor(menuBackgroundColor)
        tabMenuView.layoutParams = params
        addView(tabMenuView, 0)

        //为tabMenuView添加下划线
        val underLine = View(getContext())
        underLine.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(0.5f))
        underLine.setBackgroundColor(underlineColor)
        addView(underLine, 1)

        //初始化containerView并将其添加到DropDownMenu
        containerView = FrameLayout(context)
        containerView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        addView(containerView, 2)

    }

    /**
     * 初始化DropDownMenu
     *
     * @param tabTexts    tab标签字符串集合
     * @param popupViews   每个tab标签对应的弹出Views
     * @param contentView 主页面view
     */
    fun setDropDownMenu(tabTexts: List<String>, popupViews: List<View>, contentView: View) {
        // 抛异常,标题和弹窗页面应保持一致
        if (tabTexts.size != popupViews.size) {
            throw IllegalArgumentException("params not match, menu tab size should be equal popupViews size")
        }

        tabTexts.forEach { tabText ->
            addTab(tabText)
        }

        containerView.addView(contentView, 0)

        maskView = View(context)
        maskView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        maskView.setBackgroundColor(maskColor)
        maskView.setOnClickListener {
            (tabMenuView.getChildAt(currentTabPosition) as TextView).setTextColor(
                menuTextUnSelectedColor
            )
            closeMenu()
        }
        containerView.addView(maskView, 1)
        maskView.visibility = View.GONE

        popupMenuViews = FrameLayout(context)
        popupMenuViews.visibility = View.GONE
        containerView.addView(popupMenuViews, 2)

        popupViews.forEachIndexed { index, view ->
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            popupMenuViews.addView(view, index)
        }

    }

    private fun addTab(tabText: String) {
        val tab = TextView(context).apply {
            setSingleLine()
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize.toFloat())
            layoutParams = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
            setTextColor(menuTextUnSelectedColor)
            setCompoundDrawablesWithIntrinsicBounds(
                null, null, ContextCompat.getDrawable(context, menuUnselectedIcon), null
            )
            text = tabText
            setPadding(dp2px(5f), dp2px(12f), dp2px(5f), dp2px(12f))
        }
        //添加点击事件
        tab.setOnClickListener { switchMenu(tab) }
        tabMenuView.addView(tab)
        tabMenuView.showDividers = SHOW_DIVIDER_MIDDLE
        //添加分割线
        tabMenuView.dividerDrawable =
            ContextCompat.getDrawable(context, R.drawable.divider_line)//或者下面的方式
        /* if (i < tabTexts.size() - 1) {
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(dividerColor);
            tabMenuView.addView(view);
        }*/
    }

    private fun switchMenu(target: View) {
        for (i in 0 until tabMenuView.childCount) {
            if (target == tabMenuView.getChildAt(i)) {
                if (currentTabPosition == i) {
                    closeMenu()
                } else {
                    if (currentTabPosition == -1) {
                        popupMenuViews.visibility = View.VISIBLE
                        popupMenuViews.animation = AnimationUtils.loadAnimation(
                            context, R.anim.dd_menu_in
                        )
                        maskView.visibility = VISIBLE
                        maskView.animation = AnimationUtils.loadAnimation(
                            context, R.anim.dd_mask_in
                        )
                        popupMenuViews.getChildAt(i).visibility = VISIBLE
                    } else {
                        popupMenuViews.getChildAt(i).visibility = View.VISIBLE
                    }
                    currentTabPosition = i
                    (tabMenuView.getChildAt(i) as TextView).setTextColor(menuTextSelectedColor)
                    (tabMenuView.getChildAt(i) as TextView).setCompoundDrawablesWithIntrinsicBounds(
                        null, null, ContextCompat.getDrawable(context, menuSelectedIcon), null
                    )

                }
            } else {
                (tabMenuView.getChildAt(i) as TextView).setTextColor(menuTextUnSelectedColor)
                (tabMenuView.getChildAt(i) as TextView).setCompoundDrawablesWithIntrinsicBounds(
                    null, null, ContextCompat.getDrawable(context, menuUnselectedIcon), null
                )
                popupMenuViews.getChildAt(i).visibility = GONE
            }
        }
    }

    /**
     * 关闭菜单
     */
    fun closeMenu() {
        if (currentTabPosition != -1) {
            (tabMenuView.getChildAt(currentTabPosition) as TextView).setTextColor(
                menuTextUnSelectedColor
            )
            (tabMenuView.getChildAt(currentTabPosition) as TextView).setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(context, menuUnselectedIcon),
                null
            )
            popupMenuViews.visibility = GONE
            popupMenuViews.animation = AnimationUtils.loadAnimation(context, R.anim.dd_menu_out)
            maskView.visibility = GONE
            maskView.animation = AnimationUtils.loadAnimation(context, R.anim.dd_mask_out)
            currentTabPosition = -1
        }
    }

    /**
     * DropDownMenu是否处于可见状态
     */
    fun isShowing() = currentTabPosition != -1


    /**
     * 设置Tab文本
     */
    fun setTabText(text: String) {
        if (currentTabPosition != -1) {
            (tabMenuView.getChildAt(currentTabPosition) as TextView).text = text
        }
    }

    fun setTabClickable(clickable: Boolean) {
        var i = 0
        while (i < tabMenuView.childCount) {
            tabMenuView.getChildAt(i).isClickable = clickable
            i += 2
        }
    }

    private fun dp2px(dp: Float): Int {
        val displayMetrics = resources.displayMetrics
        return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)).toInt()
    }
}