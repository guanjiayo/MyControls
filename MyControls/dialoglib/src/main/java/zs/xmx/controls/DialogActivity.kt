package zs.xmx.controls

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import zs.xmx.controls.dialog.CommentDialog
import zs.xmx.controls.dialog.ConfirmDialog
import zs.xmx.controls.dialog.LoadingDialog
import zs.xmx.controls.dialog.ShareDialog
import zs.xmx.controls.dialog.base.DialogType

/*
 * @创建者     默小铭
 * @本类描述	  Dialog测试Activity
 * @内容说明
 *
 */
class DialogActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        initEvent()
    }


    private fun initEvent() {
        findViewById<View>(R.id.btn_confirm).setOnClickListener(this)
        findViewById<View>(R.id.btn_share).setOnClickListener(this)
        findViewById<View>(R.id.btn_comment).setOnClickListener(this)
        findViewById<View>(R.id.btn_progress).setOnClickListener(this)
    }

    private var mLoadingDialog: LoadingDialog? = null
    private var mConfirmDialog: ConfirmDialog? = null
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_confirm -> showConfirmDialog()

            R.id.btn_share -> ShareDialog.newInstance()
                .show(supportFragmentManager)

            R.id.btn_comment -> CommentDialog.newInstance()
                .show(supportFragmentManager)

            R.id.btn_progress -> {
                showLoadingDialog()
            }
        }
    }

    private fun showConfirmDialog() {
        mConfirmDialog = ConfirmDialog.newInstance(DialogType.LOGIN)
        mConfirmDialog?.show(supportFragmentManager)
    }
    
    private fun showLoadingDialog() {
        mLoadingDialog = LoadingDialog.newInstance()
            .apply {
                //setOutCancel(false)
            }
            .scheduleDismiss(800L)
            .setLabel("正在加载...")
            .setDetailLabel("正在设置")
        Log.d("TTTT", "显示弹窗")
        mLoadingDialog?.show(supportFragmentManager)

        MainScope().launch {
            delay(1000L)//模拟show()以后很快的调用dismiss()
            mLoadingDialog?.dismiss()
        }
    }
}
