package zs.xmx.controls.dialog

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast

import zs.xmx.controls.R
import zs.xmx.controls.dialog.base.BaseDialog
import zs.xmx.controls.dialog.base.BaseDialogVH

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	  评论对话框
 * @内容说明
 *
 */
class CommentDialog : BaseDialog() {

    override fun setLayout(): Int {
        return R.layout.dialog_comment
    }

    override fun convertView(holder: BaseDialogVH, dialog: BaseDialog) {
        val et_input = holder.getView<EditText>(R.id.edit_input)

        holder.setOnClickListener(R.id.tv_commit, View.OnClickListener {
            var text = et_input.text.toString().trim { it <= ' ' }
            text = if (TextUtils.isEmpty(text)) "请输入文字" else et_input.text.toString().trim { it <= ' ' }
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        })
    }

    companion object {

        fun newInstance(): CommentDialog {
            val dialog = CommentDialog()
            dialog.setDimAmount(0.5f)//背景暗度
            dialog.setGravity(Gravity.BOTTOM)//显示位置
            dialog.setAnimStyle(R.style.DialogBottomAnim)//动画样式
            return dialog
        }
    }
}
