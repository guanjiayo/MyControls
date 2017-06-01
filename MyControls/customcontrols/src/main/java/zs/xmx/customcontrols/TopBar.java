package zs.xmx.customcontrols;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/11/27 19:05
 * @本类描述	  自定义TopBar控件
 * @内容说明   1.实现父类View的构造函数
 *            2.获取attrs.xml自定义的属性
 *            3.将自定义属性和View绑定并显示出来
 *
 * @补充内容
 *
 * ---------------------------------     
 * @新增内容
 *
 */
public class TopBar extends RelativeLayout {
    private TextView tvTitle;
    private Button   leftButton, rightButton;
    private Drawable leftBackground, rightBackground;
    private String title, leftText, rightText;
    private int titleTextColor, leftTextColor, rightTextColor;
    private float        titleTextSize;
    private LayoutParams leftParams, rightParams, titleParams;

    private topbarClickListener mListener;


    public void setOnTopbarClickListener(topbarClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 其他类通过new TopBar() 时被调用
     *
     * @return
     */
    public TopBar(Context context) {
        super(context);

    }

    /**
     * 1.实现父类View的构造函数
     *
     * @param context 上下文
     * @param attrs attrs文件传过来的属性
     */
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
        //initView(context);
        initView();
        initEvent();
    }

    private void initEvent() {
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.leftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.rightClick();
            }
        });
    }

    /**
     * 2.获取attrs.xml自定义的属性
     *
     * @param context 上下文
     * @param attrs   TopBar(Context context, AttributeSet attrs)　由构造函数传过来
     */
    private void initData(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        //左侧按钮数据
        leftTextColor = typedArray.getColor(R.styleable.Topbar_leftTextColor, 0);
        leftBackground = typedArray.getDrawable(R.styleable.Topbar_leftBackground);
        leftText = typedArray.getString(R.styleable.Topbar_leftText);

        //右侧按钮数据
        rightTextColor = typedArray.getColor(R.styleable.Topbar_rightTextColor, 0);
        rightBackground = typedArray.getDrawable(R.styleable.Topbar_rightBackground);
        rightText = typedArray.getString(R.styleable.Topbar_rightText);

        //自定义标题数据
        titleTextSize = typedArray.getDimension(R.styleable.Topbar_titleTextSize, 0);
        this.title = typedArray.getString(R.styleable.Topbar_title);
        titleTextColor = typedArray.getColor(R.styleable.Topbar_titleTextColor, 0);
        //回收,避免浪费资源以及由于避免缓存报的错误
        typedArray.recycle();
    }

    /**
     * 3.1.将自定义属性和View绑定并显示出来
     * <p>
     * 用代码构建自定义控件布局
     *
     * @param context 上下文
     */
    private void initView(Context context) {
        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        //左侧Button
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        //右侧Button
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        //自定义标题
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setText(title);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setGravity(Gravity.CENTER); //居中显示文本

        setBackgroundColor(0xFF888888);

        //addRule() 是 RelativeLayout 特有的Api

        //左侧Button布局
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);//居左对齐
        addView(leftButton, leftParams);//将leftButton添加到我们的RelativeLayout 中

        //右侧Button布局
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);//居右对齐
        addView(rightButton, rightParams);//将rightButton添加到我们的RelativeLayout 中

        //自定义标题布局
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);//居中对齐
        addView(tvTitle, titleParams);//将tvTitle添加到我们的RelativeLayout 中

    }

    /**
     * 3.2.将自定义属性和View绑定并显示出来
     * <p>
     * 用打气筒xml文件构建自定义控件布局
     */
    private void initView() {
        //布局文件,根布局对象,是否作为根布局
        View rootView = View.inflate(getContext(),R.layout.custom_controls, this);

        leftButton = (Button) rootView.findViewById(R.id.btn_left);
        rightButton = (Button) rootView.findViewById(R.id.btn_right);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);

        //左侧Button
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        //右侧Button
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        //自定义标题
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setText(title);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setGravity(Gravity.CENTER); //居中显示文本
    }

    /**
     * 设置自定义标题文本内容
     * @param text
     */
    public void setTitleText(String text) {
        tvTitle.setText(text);

    }

    /**
     * 设置左侧按钮是否可见
     * @param flag
     */
    public void setLeftIsVisable(boolean flag){
        if(flag){

            leftButton.setVisibility(View.VISIBLE);

        }else{

            leftButton.setVisibility(View.GONE);
        }
    }

}
