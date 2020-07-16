package zs.xmx.rvgather

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import zs.xmx.rvgather.adapter.MainAdapter
import java.util.*

class MainActivity : AppCompatActivity() {
    private val mDatas: List<String> = ArrayList(
        listOf(
            "RecyclerView",
            "ChatRecyclerView",
            "GridViewTitle",
            "RadioSelect(单选)",
            "CheckItem(复选)",
            "CheckAndSelectItem(点击单选,长按复选)"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mRecyclerView = findViewById<RecyclerView>(R.id.id_listview_list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        val mainAdapter = MainAdapter(this, R.layout.item_list, mDatas)
        mRecyclerView.adapter = mainAdapter
        mainAdapter.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int
            ) {
                var intent: Intent? = null
                when (position) {
                    0 -> intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
                    1 -> intent = Intent(this@MainActivity, ChatRvActivity::class.java)
                    2 -> intent = Intent(this@MainActivity, GridViewTitleActivity::class.java)
                    3 -> intent = Intent(this@MainActivity, RadioSelectItemActivity::class.java)
                    4 -> intent = Intent(this@MainActivity, CheckItemActivity::class.java)
                    5 -> intent = Intent(this@MainActivity, CheckAndSelectItemActivity::class.java)
                }
                intent?.let { startActivity(it) }
            }

            override fun onItemLongClick(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int
            ): Boolean {
                return false
            }
        })
    }
}