package zs.xmx.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;

import zs.xmx.R;
import zs.xmx.utils.StatusBar;
import zs.xmx.view.KeyboardLayout;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   综合使用
 * @内容说明   1. 对于日常来说,ScrollView(根布局) + adujstResize 就能满足需求
 *            2. 如果设置图片或background,使用ScrollView(根布局) + adujstPan
 *            3.
 *
 *
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class ScrollViewByLayoutActivity extends AppCompatActivity {
    private ScrollView     scroll;
    private KeyboardLayout mKeyboardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        setContentView(R.layout.activity_scroll_layout);
        StatusBar.setStatusBarColor(this, Color.GRAY);

        mKeyboardLayout = (KeyboardLayout) findViewById(R.id.keyboardview);


        scroll = (ScrollView) findViewById(R.id.scroll_view);

        addLayoutListener();
    }

    /**
     * 监听键盘状态，布局有变化时，靠scrollView去滚动界面
     */
    public void addLayoutListener() {
        mKeyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                Log.e("onKeyboardStateChanged", "isActive:" + isActive + " keyboardHeight:" + keyboardHeight);
                if (isActive) {
                    scrollToBottom();
                }
            }
        });
    }

    /**
     * 弹出软键盘时将SVContainer滑到底
     */
    private void scrollToBottom() {

        scroll.postDelayed(new Runnable() {

            @Override
            public void run() {
                scroll.smoothScrollTo(0, scroll.getBottom() + getStatusBarHeight(ScrollViewByLayoutActivity.this));
            }
        }, 100);

    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        //获取状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }
}
