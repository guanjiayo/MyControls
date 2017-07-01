package sun_promise.softkeyboardup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author  诺诺   （http://blog.csdn.net/sun_promise）
 * @date  2016/12/15
 */
public class SoftKeyboardUpActivity extends Activity {
    private EditText et_password;
    private ImageView iv_clean_password;
    private LinearLayout ll_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_pwd);
        et_password= (EditText) findViewById(R.id.et_password);
        iv_clean_password= (ImageView) findViewById(R.id.iv_clean_password);
        ll_no_data= (LinearLayout) findViewById(R.id.ll_no_data);
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    iv_clean_password.setVisibility(View.VISIBLE);
                }else {
                    iv_clean_password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    // 设置一秒后自动弹出软键盘，若不需要自动弹出将下面代码注释即可
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 1000); // 1秒后自动弹出
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        ll_no_data.setVisibility(View.INVISIBLE);
        super.finish();
    }

    /**
     * 显示软键盘
     */
    private void showSoftInput(View v) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(v, 0);
    }
}
