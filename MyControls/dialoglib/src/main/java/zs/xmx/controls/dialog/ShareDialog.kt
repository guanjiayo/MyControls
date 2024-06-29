package zs.xmx.controls.dialog

import android.view.Gravity
import android.view.View
import android.widget.Toast
import zs.xmx.controls.R
import zs.xmx.controls.dialog.base.BaseDialog
import zs.xmx.controls.dialog.base.BaseDialogVH

/*
 * @创建者     默小铭
 * @本类描述	   分享Dialog
 * @内容说明
 *
 *
 * todo 这里用作参考,实际项目按需开发即可
 */
class ShareDialog : BaseDialog() {

    override fun setLayout(): Int {
        return R.layout.dialog_share
    }

    override fun convertView(holder: BaseDialogVH, dialog: BaseDialog) {
        holder.setOnClickListener(R.id.tv_weixin_share, View.OnClickListener {
            Toast.makeText(context, "微信分享", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        })
    }

    //todo newInstance(分享Bean) 服务器请求完,要分享的内容Bean类传进来
    companion object {

        fun newInstance(): ShareDialog {
            val dialog = ShareDialog()
            dialog.setDimAmount(0.5f)//背景暗度
            dialog.setFullscreenWidth()
            dialog.setGravity(Gravity.BOTTOM)//显示位置
            dialog.setAnimStyle(R.style.DialogBottomAnim)//动画样式
            return dialog
        }
    }
}
