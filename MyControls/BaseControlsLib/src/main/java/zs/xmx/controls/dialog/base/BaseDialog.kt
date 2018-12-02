package zs.xmx.controls.dialog.base

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import zs.xmx.controls.R


/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	   BaseDialog
 * @内容说明   onCreateView方式实现,便于后续扩展
 *
 */

/**
 * 创建 DialogFragment 有两种方式:
 * 1. 重写 onCreateDialog()
 * 一般用于创建替代传统的 Dialog 对话框的场景，UI 简单，功能单一
 * 2. 重写 onCreateView()
 * 一般用于创建复杂内容弹窗或全屏展示效果的场景，UI 复杂，功能复杂，一般有网络请求等异步操作。
 *
 */
abstract class BaseDialog : AppCompatDialogFragment() {

    @LayoutRes
    protected var mLayoutResId: Int = 0//布局LayoutID
    private lateinit var mContext: Context//上下文
    private var mDimAmount = 0.5f//背景昏暗度
    private var mMargin = 0//左右边距
    private var mAnimStyle = 0//进入退出动画
    private var mOutCancel = true//点击屏幕和返回键取消
    private var mBtnCancel = true//点击屏幕不取消,但是返回键取消
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mGravity = Gravity.CENTER//dialog 显示位置(默认居中显示)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog)
        mLayoutResId = setLayoutId()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(mLayoutResId, container, false)
        convertView(BaseDialogVH.create(view), this)
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }


    private fun initParams() {
        val window = dialog.window
        if (window != null) {
            val params = window.attributes
            params.dimAmount = mDimAmount

            //设置dialog显示位置
            params.gravity = mGravity

            /**由于DialogFragment 默认会让布局自带边距,所以我们如下设置Dialog狂傲**/
            //设置dialog宽度
            if (mWidth == 0) {
                params.width = getScreenWidth(mContext) - 2 * dp2px(mContext, mMargin.toFloat())
            } else {
                params.width = dp2px(mContext, mWidth.toFloat())
            }

            //设置dialog高度
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
            } else {
                params.height = dp2px(mContext, mHeight.toFloat())
            }


            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle)
            }

            window.attributes = params
        }

        isCancelable = mOutCancel
        dialog.setCanceledOnTouchOutside(mBtnCancel)

    }

    /**
     * 设置背景昏暗度
     */
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float): BaseDialog {
        mDimAmount = dimAmount
        return this
    }

    /**
     * 设置显示位置
     */
    fun setGravity(gravity: Int): BaseDialog {
        mGravity = gravity
        return this
    }

    /**
     * 设置宽高 单位是dp
     *
     */
    fun setSize(width: Int, height: Int): BaseDialog {
        mWidth = width
        mHeight = height
        return this
    }

    /**
     * 设置左右margin比例
     */
    fun setMargin(margin: Int): BaseDialog {
        mMargin = margin
        return this
    }

    /**
     * 设置进入退出动画
     */
    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialog {
        mAnimStyle = animStyle
        return this
    }

    /**
     * true: dialog弹出后,点击屏幕和物理返回键,dialog消失
     * false: dialog弹出后,点击屏幕和物理返回键,dialog不消失
     */
    fun setOutCancel(outCancel: Boolean): BaseDialog {
        mOutCancel = outCancel
        return this
    }

    /**
     * true: 点击屏幕，dialog消失；点击返回键dialog消失
     * false: 点击屏幕，dialog不消失；点击返回键dialog消失
     */
    fun setCanceledOnTouchOutside(btnCancel: Boolean): BaseDialog {
        mBtnCancel = btnCancel
        return this
    }


    /**
     * 显示Dialog
     */
    fun show(manager: FragmentManager): BaseDialog {
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }


    /**
     * 设置dialog布局
     */
    abstract fun setLayoutId(): Int

    /**
     * 操作dialog布局
     */
    abstract fun convertView(holder: BaseDialogVH, dialog: BaseDialog)


    /**
     * 获取屏幕宽度
     */
    private fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    /**
     * dp转px
     */
    private fun dp2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }


}
