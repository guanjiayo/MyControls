package zs.xmx.controls.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import zs.xmx.controls.R
import zs.xmx.controls.dialog.base.BaseDialog
import zs.xmx.controls.dialog.base.BaseDialogVH
import zs.xmx.controls.dialog.base.DialogType

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	   通知对话框
 * @内容说明    1. "确认/取消" 按钮
 *             2. "确认" 按钮
 *
 * todo 这里用作参考,实际项目按需开发即可
 */
class ConfirmDialog : BaseDialog() {

    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments ?: return
        type = bundle.getString("type")
    }

    override fun setLayout(): Int {
        return R.layout.dialog_confirm
    }

    override fun convertView(holder: BaseDialogVH, dialog: BaseDialog) {
        getDialog()?.setCanceledOnTouchOutside(false)
        if (DialogType.LOGIN == type) {
            holder.setText(R.id.title, "提示")
            holder.setText(R.id.message, "是否退出登录?")
        } else if ("2" == type) {
            holder.setText(R.id.title, "警告")
            holder.setText(R.id.message, "您的帐号已被冻结!")
        }

        holder.setOnClickListener(R.id.cancel, View.OnClickListener { dialog.dismiss() })
        holder.setOnClickListener(R.id.confirm, View.OnClickListener {
            dialog.dismiss()
            Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show()
        })
    }

    companion object {

        fun newInstance(type: String): ConfirmDialog {
            val bundle = Bundle()
            bundle.putString("type", type)
            val dialog = ConfirmDialog()
            dialog.setDimAmount(0.3f)
            dialog.setMargin(60)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setAnimStyle(R.style.ConfirmDialogAnim)//动画样式
            dialog.arguments = bundle
            return dialog
        }
    }
}
