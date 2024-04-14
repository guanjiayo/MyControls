package zs.xmx.controls.dialog.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.FloatRange
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
 *  ps: 仅适用于Dialog布局宽高固定时使用
 */
abstract class BaseDialog : DialogFragment() {

    private lateinit var mContext: Context//上下文
    private var mDimAmount = 0.3f//背景昏暗度
    private var mAnimStyle = 0//进入退出动画
    private var mInvisibleDismiss = true//当页面不可见时,dismiss
    private var mIsFullScreen = false//是否全屏显示(有可能是宽度全屏或者宽高全屏)
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mGravity = Gravity.CENTER//dialog 显示位置(默认居中显示)

    private var isSetOnTouchOutside = false//通知在getDialog()不为空时设置CancelOnTouchOutside属性
    private var mIsCancel = true//setCancelOnTouchOutside(isCancel)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = when {
            setLayout() is Int -> inflater.inflate(setLayout() as Int, container, false)
            setLayout() is View -> setLayout() as View
            else -> throw ClassCastException("type of setLayout() must be int or View!")
        }
        convertView(BaseDialogVH.create(view), this)
        return view

    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    override fun onPause() {
        super.onPause()
        if (mInvisibleDismiss) {
            dismiss()
        }
    }

    private fun initViews() {
        val window = dialog?.window
        if (window != null) {
            val params = window.attributes
            params.dimAmount = mDimAmount

            //设置dialog显示位置
            params.gravity = mGravity

            /**由于DialogFragment 默认会让布局自带边距,所以我们如下设置Dialog宽高**/
            if (mIsFullScreen) {
                if (mWidth != 0) { //两条件满足 => 宽度全屏
                    params.width = mWidth
                } else {
                    params.width = WindowManager.LayoutParams.MATCH_PARENT
                    params.height = WindowManager.LayoutParams.MATCH_PARENT
                }
            } else {
                if (mWidth != 0) {
                    params.width = dp2px(mContext, mWidth.toFloat())
                    params.height = dp2px(mContext, mHeight.toFloat())
                }
            }

            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle)
            }

            window.attributes = params
        }

        if (isSetOnTouchOutside) {
            dialog?.setCanceledOnTouchOutside(mIsCancel)
        }

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
     */
    fun setSize(width: Int, height: Int): BaseDialog {
        if (!mIsFullScreen) {
            mWidth = width
            mHeight = height
        } else {
            throw IllegalStateException("Cannot set width and height when in fullscreen mode.")
        }
        return this
    }

    /**
     * 设置全屏(宽高)
     */
    fun setFullscreenMode(): BaseDialog {
        mIsFullScreen = true
        return this
    }

    /**
     * 设置宽全屏
     */
    fun setFullscreenWidth(): BaseDialog {
        mWidth = WindowManager.LayoutParams.MATCH_PARENT
        mIsFullScreen = true
        return this
    }

    /**
     * 设置进入退出动画
     */
    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialog {
        mAnimStyle = animStyle
        return this
    }

    /*
        以下两个方法根据不同组合效果不一样

        dialog.setCancelable(true);
        #: 2种方式都可以使得对话框消失

        dialog.setCancelable(false);
        #: 2种方式都不能使得对话框消失

        dialog.setCanceledOnTouchOutside(true);
        #: 2种方式都可以使得对话框消失

        dialog.setCanceledOnTouchOutside(false);
        #: 只有手机返回按键可使得对话框消失

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        #: 2种方式都不能使得对话框消失

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        #: 2种方式都可以使得对话框消失

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        #: 2种方式都可以不能使得对话框消失（和上面一种情况对比，顺序不同，产生的结果也是不同的，注意！）

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        #: 2种方式都可以使得对话框消失

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        #: 只有手机返回按键可使得对话框消失

     */

    /**
     * true: dialog弹出后,点击物理返回键,点击屏幕 dialog消失
     * false: dialog弹出后,点击物理返回键,点击屏幕 dialog不消失
     */
    fun setOutCancel(outCancel: Boolean): BaseDialog {
        isCancelable = outCancel
        return this
    }

    /**
     * true: 点击屏幕，点击物理返回键 dialog消失；
     * false: 点击屏幕，dialog不消失, 点击物理返回键, dialog消失
     *
     * ps: 需要在onViewCreated生命周期调用,否则dialog为null
     */
    fun setCanceledOnTouchOutside(btnCancel: Boolean): BaseDialog {
        isSetOnTouchOutside = true
        mIsCancel = btnCancel
        return this
    }

    /**
     * true: 页面不可见时隐藏Dialog
     * false: 页面不可见时,不隐藏Dialog
     */
    fun setInvisibleDismiss(dismiss: Boolean): BaseDialog {
        mInvisibleDismiss = dismiss
        return this
    }

    /**
     * Dialog 是否可见
     */
    val isShowing: Boolean
        get() = dialog?.isShowing == true


    /**
     * 显示Dialog
     */
    fun show(manager: FragmentManager): BaseDialog {
        val tag = System.currentTimeMillis().toString()
        val fragment = manager.findFragmentByTag(tag)
        if (fragment != null) {
            val fragmentTransaction: FragmentTransaction = manager.beginTransaction()
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }
        try {
            super.show(manager, tag)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return this
    }

    /**
     * 设置dialog布局
     */
    abstract fun setLayout(): Any

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