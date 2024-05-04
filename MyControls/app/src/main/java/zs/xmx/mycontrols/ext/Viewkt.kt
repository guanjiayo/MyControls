@file:Suppress("UNCHECKED_CAST")

package zs.xmx.mycontrols.ext

import android.view.View


/*
    扩展点击事件,兼容this的情况
    使用: btn_regiest.onClick(this)
 */
fun View.onClick(listener: View.OnClickListener?): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法(lambda形式)
    使用: btn_regiest.onClick{ 点击后续操作  }
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/**
 * 扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun <T : View> T.singleClick(time: Long = 800, block: (T) -> Unit) {
    delayClickTime = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

/*
   兼容点击事件设置为this的情况
   不传参,默认为800毫秒
   tvTest.singleClick(1500) {
               Log.e("singClick",System.currentTimeMillis().toString())
           }
 */

fun <T : View> T.singleClick(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        delayClickTime = time
        setOnClickListener {
            if (clickEnable()) {
                onClickListener.onClick(this)
            }
        }

    }
}

//给这两个变量添加默认id
private var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

private var <T : View> T.delayClickTime: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1L
    set(value) {
        setTag(1123461123, value)
    }

//判断是否可以点击
private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - lastClickTime >= delayClickTime) {
        flag = true
    }
    lastClickTime = currentClickTime
    return flag
}


//TODO  防抖动,待验证
inline fun View.setThrottleListener(delayMillis: Long = 1000L, crossinline onClick: () -> Unit) {
    this.setOnClickListener {
        this.isClickable = false
        onClick()
        this.postDelayed({
            this.isClickable = true
        }, delayMillis)
    }
}

