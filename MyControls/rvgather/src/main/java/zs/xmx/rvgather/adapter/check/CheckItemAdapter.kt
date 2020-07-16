package zs.xmx.rvgather.adapter.check

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import zs.xmx.rvgather.R


/**
 * 单选Adapter
 */
class CheckItemAdapter(private val mContext: Context) :
    RecyclerView.Adapter<CheckItemAdapter.ViewHolder>() {
    //使用一个map记录Item选中的位置,如果需要默认值可用自行设置
    var positionSet = mutableSetOf<Int>()

    private var onItemClickListener: OnItemClickListener? = null

    private var mDatas = mutableListOf<String>()

    fun setData(datas: MutableList<String>?) {
        mDatas.clear()
        mDatas.addAll(datas!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.item_radio_select, null, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return if (mDatas.size == 0) 0 else mDatas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItem.text = mDatas[position]
        holder.rbItem.isChecked = positionSet.contains(position)
        holder.itemView.setOnClickListener {
            //先处理完数据再回调结果
            addOrRemove(position)
            onItemClickListener?.onItemClick(it, position)
        }
    }

    private fun addOrRemove(position: Int) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            positionSet.remove(position)
        } else {
            // 如果不包含，则添加
            positionSet.add(position)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.text)
        var rbItem: RadioButton = itemView.findViewById(R.id.radioButton)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}