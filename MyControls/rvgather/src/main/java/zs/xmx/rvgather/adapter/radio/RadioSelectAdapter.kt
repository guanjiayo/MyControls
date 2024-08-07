package zs.xmx.rvgather.adapter.radio

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
 *
 * 这里使用的方案是使用一个变量作为标记
 */
class RadioSelectAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RadioSelectAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null
    private var mDatas = mutableListOf<String>()

    //单选标记,如果需要默认值,可以设置为0
    private val defPosition = -1

    //最后选中的position
    private var lastClickPosition = defPosition

    fun setData(datas: MutableList<String>?) {
        mDatas.clear()
        mDatas.addAll(datas!!)
        notifyDataSetChanged()
    }

    /**
     * 设置item选中状态
     */
    private fun setSelect(position: Int) {
        //已选中的不再处理
        if (lastClickPosition == position) return
        //取消之前选中的状态
        if (lastClickPosition != defPosition) {
            notifyItemChanged(lastClickPosition)
        }
        //更新最新的选中状态
        lastClickPosition = position
        notifyItemChanged(lastClickPosition)
    }

    /**
     * 恢复默认选中状态
     */
    fun resetSelect() {
        if (lastClickPosition != defPosition) {
            notifyItemChanged(lastClickPosition)
        }
        lastClickPosition = defPosition
        notifyItemChanged(lastClickPosition)
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
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(it, position)
            setSelect(position)
        }
        holder.rbItem.isChecked = position == lastClickPosition
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