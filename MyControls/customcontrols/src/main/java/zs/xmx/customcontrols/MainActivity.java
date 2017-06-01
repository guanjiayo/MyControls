package zs.xmx.customcontrols;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setLeftIsVisable(true);
        topBar.setOnTopbarClickListener(new topbarClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_SHORT).show();
                topBar.setTitleText("Back");
            }

            @Override
            public void rightClick() {
                Toast.makeText(MainActivity.this, "More", Toast.LENGTH_SHORT).show();
                topBar.setTitleText("More");
            }
        });
    }


}
