package zs.xmx.controls

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import zs.xmx.controls.dialog.CommentDialog
import zs.xmx.controls.dialog.ConfirmDialog
import zs.xmx.controls.dialog.LoadingDialog
import zs.xmx.controls.dialog.ShareDialog
import zs.xmx.controls.dialog.base.DialogType

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
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

    private val mLoadingDialog = LoadingDialog.newInstance()
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_confirm -> ConfirmDialog.newInstance(DialogType.LOGIN)
                .show(supportFragmentManager)

            R.id.btn_share -> ShareDialog.newInstance()
                .show(supportFragmentManager)

            R.id.btn_comment -> CommentDialog.newInstance()
                .show(supportFragmentManager)

            R.id.btn_progress -> {
                mLoadingDialog
                    .setLabel("正在加载")
                    .setDetailLabel("正在设置")
                    .scheduleDismiss(3000L)
                    .show(supportFragmentManager)
            }
        }
    }
}
