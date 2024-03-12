package zs.xmx.lib_ddmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CityDropDownAdapter(val context: Context, private val list: MutableList<String>) :
    RecyclerView.Adapter<CityDropDownAdapter.CityViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    private var checkItemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(itemView)
    }

    override fun getItemCount(): Int = if (list.size == 0) 0 else list.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val cityBean = list[position]
        holder.mText.text = cityBean
        if (checkItemPosition == position) {
            holder.mText.setTextColor(
                ContextCompat.getColor(context, R.color.drop_down_selected)
            )
            holder.mText.setCompoundDrawablesWithIntrinsicBounds(
                null, null, ContextCompat.getDrawable(context, R.mipmap.drop_down_checked), null
            )
        } else {
            holder.mText.setTextColor(
                ContextCompat.getColor(context, R.color.drop_down_unselected)
            )
            holder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(it, position)
        }
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

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mText: TextView = itemView.findViewById(R.id.text)
    }

}