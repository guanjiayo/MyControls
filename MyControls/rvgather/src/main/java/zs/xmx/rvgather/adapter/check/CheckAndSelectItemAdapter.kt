package zs.xmx.rvgather.adapter.check

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import zs.xmx.rvgather.CheckAndSelectItemActivity
import zs.xmx.rvgather.CheckItemActivity
import zs.xmx.rvgather.R


/**
 * 单选和复选共存Adapter
 */
class CheckAndSelectItemAdapter(private val mContext: Context) :
    RecyclerView.Adapter<CheckAndSelectItemAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var mDatas = mutableListOf<String>()
    //使用一个map记录Item选中的位置,如果需要默认值可用自行设置
    var positionSet = mutableSetOf<Int>()
    //单选还是复选模式
    private var selectMode = false

    fun setData(datas: MutableList<String>?) {
        mDatas.clear()
        mDatas.addAll(datas!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.item_check_select_select, null, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return if (mDatas.size == 0) 0 else mDatas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItem.text = mDatas[position]
        holder.itemView.setOnClickListener {
            if (selectMode) {
                // 如果当前处于多选状态，则进入多选状态的逻辑
                // 维护当前已选的position
                addOrRemove(position)
            } else {
                // 如果不是多选状态，则进入单选事件的业务逻辑
                if (!positionSet.contains(position)) {
                    // 选择不同的单位时取消之前选中的单位
                    positionSet.clear()
                }
                addOrRemove(position)
            }
            onItemClickListener?.onItemClick(it, position)
        }
        holder.itemView.setOnLongClickListener {
            if (!selectMode) {
                selectMode = true
                positionSet.clear()
            }
            onItemClickListener?.onItemLongClick(it, position)
            return@setOnLongClickListener false
        }
        holder.rbItem.isChecked = positionSet.contains(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.text)
        var rbItem: RadioButton = itemView.findViewById(R.id.radioButton)
    }

    private fun addOrRemove(position: Int) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            positionSet.remove(position)
        } else {
            // 如果不包含，则添加
            positionSet.add(position)
        }
        if (positionSet.size == 0) {
            // 如果没有选中任何的item，则退出多选模式
            notifyDataSetChanged()
            selectMode = false
        } else {
            // 更新列表界面，否则无法显示已选的item
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

}