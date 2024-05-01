package zs.xmx.mycontrols.toolbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import zs.xmx.mycontrols.R

/**
 * 封装的ToolBar展示页
 * 1. HeaderBar
 * 2. SearchBar
 */
class ToolBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_bar)
    }
}