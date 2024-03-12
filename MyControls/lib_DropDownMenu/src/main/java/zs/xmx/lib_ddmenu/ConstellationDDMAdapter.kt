package zs.xmx.lib_ddmenu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ConstellationDDMAdapter(val context: Context) :
    RecyclerView.Adapter<ConstellationDDMAdapter.ConstellationViewHolder>() {

    private var mDataList = mutableListOf<String>()

    private var checkItemPosition = 0

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstellationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_constellation, parent, false)
        return ConstellationViewHolder(itemView)
    }

    override fun getItemCount(): Int = if (mDataList.size == 0) 0 else mDataList.size

    override fun onBindViewHolder(holder: ConstellationViewHolder, position: Int) {
        val entity = mDataList[position]
        holder.mText.text = entity
        if (checkItemPosition == position) {
            holder.mText.setTextColor(
                ContextCompat.getColor(context, R.color.drop_down_selected)
            )
            holder.mText.setBackgroundResource(R.drawable.check_bg)
        } else {
            holder.mText.setTextColor(
                ContextCompat.getColor(context, R.color.drop_down_unselected)
            )
            holder.mText.setBackgroundResource(R.drawable.uncheck_bg)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(it, position)
        }
    }

    fun setData(dataList: MutableList<String>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setCheckItem(position: Int) {
        checkItemPosition = position
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class ConstellationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mText: TextView = itemView.findViewById(R.id.text)
    }

}