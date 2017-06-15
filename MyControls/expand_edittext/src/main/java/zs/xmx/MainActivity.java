package zs.xmx;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import zs.xmx.weight.ClearEditText;
import zs.xmx.weight.DrawableEditText;
import zs.xmx.weight.PasswordEditText;

/**
 *
 *
 *
 *
 *
 *
 */
/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/15
 * @本类描述
 * @内容说明
 *       实际使用,把这个moudle做成library,这里只是方便测试
 *       <p>
 *       注意:使用这个记得导入design库
 *       <p>
 *       整个项目的继承关系:
 *       DrawableEditText<-ClearEditText<-PasswordEditText
 * @补充内容 TODO:
 *           1. 使用SVG替换掉这里的Drawable
 *           2. 添加tint/tintMode属性设置图片颜色
 *           3. EditText 内字体大小适配
 *           4. 模仿TextInputLayout实现浮动标签
 *           5. 自定义EditText光标实现波纹动画
 *
 *
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class MainActivity extends AppCompatActivity {
    private ClearEditText    clearEditText;
    private PasswordEditText passwordEditText;
    private ClearEditText    passwordEditText_til;
    private DrawableEditText drawableEditText;
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //软键盘适应屏幕
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        drawableEditText = (DrawableEditText) findViewById(R.id.et_drawable);
        clearEditText = (ClearEditText) findViewById(R.id.et_clear);
        passwordEditText_til = (ClearEditText) findViewById(R.id.et_pwd_til);
        passwordEditText = (PasswordEditText) findViewById(R.id.et_pwd);
        textInputLayout = (TextInputLayout) findViewById(R.id.editext_til);
    }


    public void pwd_til(View view) {
        passwordEditText_til.startShakeAnimation();
        textInputLayout.setError("passwordEditText_til error");
    }

    public void pwd(View view) {
        passwordEditText.setError("passwordEditText error");
        passwordEditText.startShakeAnimation();

    }
}
