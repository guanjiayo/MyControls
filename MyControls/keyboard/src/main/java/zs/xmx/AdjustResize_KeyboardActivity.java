package zs.xmx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述	  adjustResize(软键盘测试)
 * @内容说明   1. 全屏模式下,adjustResize失效
 *            2. 设置了沉浸式状态栏但不设置fitSystemWindow为true时,adjustResize失效
 *            3. 弹出软键盘时不会将上方布局挤出屏幕外
 *            4. 当控件设置了background属性,或设置了权重的imageView会发生变形
 *            5. 当使用LinearLayout作为输入框的父布局时,若软键盘高度大于输入框,输入框会被覆盖掉
 *
 * @补充内容  //TODO 写一个方法替换根布局
 *           //TODO 布局加一个ListView
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class AdjustResize_KeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        setContentView(R.layout.activity_resize);


    }


    public void FullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
    }

    public void CancelFullScreen(View view) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏

    }
}
