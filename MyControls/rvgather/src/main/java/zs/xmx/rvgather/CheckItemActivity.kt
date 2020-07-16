package zs.xmx.rvgather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import zs.xmx.rvgather.adapter.check.CheckItemAdapter
import zs.xmx.rvgather.databinding.ActivityCheckBinding

/**
 * 复选Item
 */
class CheckItemActivity : AppCompatActivity(), CheckItemAdapter.OnItemClickListener {

    private var mDatas = mutableListOf<String>()
    private lateinit var adapter: CheckItemAdapter
    private lateinit var binding: ActivityCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initData()
        initEvent()
    }

    private fun initData() {
        for (datas in 0..50) {
            mDatas.add("这是一个Item  $datas")
        }
    }

    private fun initEvent() {
        val rvList = binding.rvList
        adapter = CheckItemAdapter(this@CheckItemActivity)
        val layoutManager = LinearLayoutManager(this@CheckItemActivity)
        rvList.layoutManager = layoutManager
        adapter.setData(mDatas)
        binding.rvList.adapter = adapter
        adapter.setOnItemClickListener(this@CheckItemActivity)
    }


    override fun onItemClick(view: View, position: Int) {
        val positionSet = adapter.positionSet
        var num = 0
        for (index in 0..mDatas.size) {
            if (positionSet.contains(index)) {
                num++
            }
        }
        Toast.makeText(this, "选中的item  num:   $num", Toast.LENGTH_SHORT).show()
    }

}