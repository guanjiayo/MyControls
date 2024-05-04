package zs.xmx.mycontrols.toolbar.headbar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.viewbinding.binding
import zs.xmx.mycontrols.databinding.ActivityHeadBarBinding

class HeadBarActivity : AppCompatActivity() {

    private val mBinding by binding<ActivityHeadBarBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.titleBar.setLeftTextViewListener {
            Toast.makeText(this@HeadBarActivity, "${it.text}", Toast.LENGTH_SHORT).show()
        }
        mBinding.titleBar.setRightTextViewListener {
            Toast.makeText(this@HeadBarActivity, "${it.text}", Toast.LENGTH_SHORT).show()
        }
    }
}