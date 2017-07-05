package zs.xmx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
public class Complex_KeyboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE); // 去除标题  必须在setContentView()方法之前调用
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 设置全屏
        setContentView(R.layout.activity_complex);
      //  AndroidBug5497Workaround.assistActivity(this);

    }
}
