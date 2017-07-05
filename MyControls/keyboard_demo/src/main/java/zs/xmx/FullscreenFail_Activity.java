package zs.xmx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import zs.xmx.utils.StatusBar;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   AdjustResize失效处理(全屏模式)
 * @内容说明   解决:  使用5497类(FrameLayout_KeyboardUtils),动态监听软键盘弹出位置
 *             但是这种方式会使上方布局(包括标题栏挤出屏幕外)
 *
 *
 *
 *
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class FullscreenFail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_fullscreenfail);
        StatusBar.setStatusBarColor(this,Color.BLUE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        //FrameLayout_KeyboardUtils.assistActivity(ResizeInvalid_Activity.this);
    }


}
