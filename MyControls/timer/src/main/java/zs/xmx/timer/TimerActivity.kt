package zs.xmx.timer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import zs.xmx.timer.databinding.ActivityTimerBinding

/*
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:  倒计时器实践
 *        1. 不用管线程问题
 *        2. 随便改造成倒计时或计数器
 *        3. 可以中途修改计数值
 */
class TimerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimerBinding
    private val mHandler: Handler
    private var mCount = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initEvent()
    }

    private fun initEvent() {
        binding.btnTimer.setOnClickListener {
            if (binding.etTime.text.isNotEmpty()) {
                mCount = Integer.parseInt(binding.etTime.text.toString())
                mHandler.post(countDown)
            } else {
                Toast.makeText(this, "输入不能为空!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    init {
        mHandler = Handler()
    }

    /*
        倒计时
     */
    @SuppressLint("SetTextI18n")
    private val countDown = object : Runnable {

        override fun run() {
            binding.tvTimer.text = mCount.toString()
            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                removeRunnable()
            }
            mCount--
        }
    }

    fun removeRunnable() {
        mHandler.removeCallbacks(countDown)
    }

}