package zs.xmx.rvgather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import zs.xmx.rvgather.adapter.radio.RadioSelectAdapter
import zs.xmx.rvgather.databinding.ActivitySelectBinding


/**
 * 单选item的RecycleView例子
 */
class RadioSelectItemActivity : AppCompatActivity(), RadioSelectAdapter.OnItemClickListener {

    private lateinit var binding: ActivitySelectBinding

    private var mDatas = mutableListOf<String>()

    private lateinit var radioSelectAdapter: RadioSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(LayoutInflater.from(this))
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
        radioSelectAdapter = RadioSelectAdapter(this@RadioSelectItemActivity)
        val layoutManager = LinearLayoutManager(this@RadioSelectItemActivity)
        rvList.layoutManager = layoutManager
        radioSelectAdapter.setData(mDatas)
        binding.rvList.adapter = radioSelectAdapter
        radioSelectAdapter.setOnItemClickListener(this@RadioSelectItemActivity)
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this, mDatas[position], Toast.LENGTH_SHORT).show()
    }


}
