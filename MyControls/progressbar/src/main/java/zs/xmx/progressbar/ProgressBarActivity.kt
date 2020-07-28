package zs.xmx.progressbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import zs.xmx.progressbar.databinding.ActivityProgressBarBinding

/*
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:  倒计时器实践
 *        1. 不用管线程问题
 *        2. 随便改造成倒计时或计数器
 *        3. 可以中途修改计数值
 */
class ProgressBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBarBinding
    private val MSG_UPADTE = 0X110
    private val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            var progress: Int = binding.progressHorizontal.progress
            var progress2: Int = binding.progressRound.progress
            binding.progressHorizontal.progress = ++progress
            binding.progressRound.progress = ++progress2
            if (progress >= 100||progress2 >= 100) {
                this.removeMessages(MSG_UPADTE)
            } else {
                sendEmptyMessageDelayed(MSG_UPADTE, 150)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBarBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initEvent()
    }

    private fun initEvent() {
        mHandler.sendEmptyMessage(MSG_UPADTE)
    }


}