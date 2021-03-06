package zs.xmx.mycontrols

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import zs.xmx.ExpandEditTextActivity
import zs.xmx.controls.DialogActivity
import zs.xmx.progressbar.ProgressBarActivity
import zs.xmx.rvgather.GatherRecycleViewActivity
import zs.xmx.timer.TimerActivity
import zs.xmx.wygridlayout.DragedGridLayoutActivity
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var mList: MutableList<String>
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        initEvent()
    }

    private fun initEvent() {
        listView.adapter = ArrayAdapter(this, R.layout.activity_main, mList)
        listView.onItemClickListener = this
    }

    private fun initData() {
        mList = ArrayList()
        mList.add("DialogFragment")
        mList.add("RecycleView")
        mList.add("ExpandEditText")
        mList.add("DragGridLayout")
        mList.add("Timer")
        mList.add("ProgressBar")
    }

    private fun initView() {
        listView = ListView(this)
        setContentView(listView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT))

    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (position) {
            0 -> startActivity(Intent(this, DialogActivity::class.java))
            1 -> startActivity(Intent(this, GatherRecycleViewActivity::class.java))
            2 -> startActivity(Intent(this, ExpandEditTextActivity::class.java))
            3 -> startActivity(Intent(this, DragedGridLayoutActivity::class.java))
            4 -> startActivity(Intent(this, TimerActivity::class.java))
            5 -> startActivity(Intent(this, ProgressBarActivity::class.java))
        }
    }


}
