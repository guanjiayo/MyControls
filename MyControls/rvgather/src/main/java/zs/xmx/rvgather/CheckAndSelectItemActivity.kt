package zs.xmx.rvgather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import zs.xmx.rvgather.adapter.check.CheckAndSelectItemAdapter
import zs.xmx.rvgather.databinding.ActivityCheckSelectBinding

class CheckAndSelectItemActivity : AppCompatActivity(), CheckAndSelectItemAdapter.OnItemClickListener {
    private var mDatas = mutableListOf<String>()
    private lateinit var adapter: CheckAndSelectItemAdapter
    private lateinit var binding: ActivityCheckSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckSelectBinding.inflate(LayoutInflater.from(this))
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
        adapter = CheckAndSelectItemAdapter(this@CheckAndSelectItemActivity)
        val layoutManager = LinearLayoutManager(this@CheckAndSelectItemActivity)
        rvList.layoutManager = layoutManager
        adapter.setData(mDatas)
        binding.rvList.adapter = adapter
        adapter.setOnItemClickListener(this@CheckAndSelectItemActivity)
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

    override fun onItemLongClick(view: View, position: Int) {

    }



}