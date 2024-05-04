package zs.xmx.mycontrols.toolbar

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.databinding.ActivityToolBarBinding
import zs.xmx.mycontrols.ext.onClick
import zs.xmx.mycontrols.toolbar.headbar.HeadBarActivity

/**
 * 封装的ToolBar展示页
 * 1. HeaderBar
 * 2. SearchBar
 */
class ToolBarActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityToolBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityToolBarBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //标题栏
        mBinding.actionHeadBar.setOnClickListener {
            startActivity(Intent(this@ToolBarActivity, HeadBarActivity::class.java))
        }
        //搜索栏
        mBinding.actionSearchBar.setOnClickListener {

        }

    }
}