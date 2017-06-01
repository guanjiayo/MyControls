package zs.xmx.wygridlayout;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.List;

import zs.xmx.wygridlayout.utils.DensityUtils;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/3/16
 * @本类描述	  仿网易新闻频道管理
 * @内容说明   1.定义出一个setItem方法,动态地将每个频道的标题数据集合传递进来
 *            2.定义出一个setAllowDrag方法,控制控件是否可拖拽
 *            3.定义一个setOnDragItemClickListener接口回调处理item点击事件
 *            4.addItem方法对外开放,方便添加新的Item
 *
 * @补充内容  注意:
 *            ViewGroup中移除view,由于removeView需要时间,因此不能直接addView,否者会报错
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

public class DragedGridLayout extends GridLayout implements View.OnDragListener, View.OnLongClickListener, View.OnClickListener {
    private static final int   columnCount  = 4;//列数
    private static final float maginValue   = 5;//控件的magin值
    private static final float paddingValue = 5;//控件的padding值
    private boolean allowdrag;//记录当前控件是否可拖拽
    private View    dragedView;//当前点击的itemView


    public DragedGridLayout(Context context) {
        this(context, null);
    }

    public DragedGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setColumnCount(columnCount);
        this.setLayoutTransition(new LayoutTransition());
    }

    public DragedGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void setItem(List<String> items) {
        for (String item : items) {
            addItem(item);
        }
    }

    public void addItem(String item) {
        TextView textView = newItemView();
        textView.setText(item);
        this.addView(textView, 0);
    }

    private TextView newItemView() {
        //往GridLayout添加TextView
        TextView tv = new TextView(getContext());

        tv.setGravity(Gravity.CENTER);

        int padding = DensityUtils.px2dip(getContext(), paddingValue);
        ;
        tv.setPadding(padding, padding, padding, padding);
        tv.setBackgroundResource(R.drawable.tv_bg_selector);

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        //textView的外边距,注意这里的5为px,要转为dp
        int margin = DensityUtils.px2dip(getContext(), maginValue);
        //当前屏幕像素点的columnCount分之一,减去2个超出边界的margin
        layoutParams.width = getResources().getDisplayMetrics().widthPixels / columnCount - margin * 2;
        layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.setMargins(margin, margin, margin, margin);
        tv.setLayoutParams(layoutParams);
        if (this.allowdrag) {
            tv.setOnLongClickListener(this);
        } else {
            tv.setOnLongClickListener(null);
        }
        //设置item点击事件
        tv.setOnClickListener(this);

        return tv;
    }

    public void setAllowDrag(boolean allowDrag) {
        this.allowdrag = allowDrag;
        if (this.allowdrag) {
            this.setOnDragListener(this);
        } else {
            this.setOnDragListener(null);
        }
    }

    /**
     * ACTION_DRAG_STARTED 当拖拽操作执行时,执行一次
     * ACTION_DRAG_ENDED 当拖拽事件结束,手指抬起时,执行一次
     * ACTION_DRAG_ENTERED 当手指进入设置了拖拽监听的控件范围内执行一次
     * ACTION_DRAG_EXITED 当手指离开设置了拖拽监听的控件范围内执行一次
     * ACTION_DRAG_LOCATION 当手指在设置了拖拽监听的控件范围内,移动时,实时执行多次
     * ACTION_DROP 当手指在设置了拖拽监听的控件范围内松开时,执行一次
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch (event.getAction()) {
            //当拖拽事件开始时,创建出与子控件对应的矩形数组
            case DragEvent.ACTION_DRAG_STARTED:
                initRects();
                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                //手指一动时,实现判断触摸是否进入了某一子控件,拿到对应索引
                int touchIndex = getTouchIndex(event);
                //说明触摸点进入了某一个子控件,判断被拖拽的View与进入的子控件View不是同一个,才执行删除添加操作
                if (touchIndex > -1 && dragedView != null && dragedView != DragedGridLayout.this.getChildAt(touchIndex)) {
                    DragedGridLayout.this.removeView(dragedView);
                    DragedGridLayout.this.addView(dragedView, touchIndex);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //拖拽事件结束后,背景设置为可用
                if (dragedView != null) {
                    dragedView.setEnabled(true);
                }
                break;
        }
        return true;
    }

    private int getTouchIndex(DragEvent event) {
        //遍历所有的数组,如果包含了当前的触摸点,返回index
        for (int i = 0; i < mRects.length; i++) {
            Rect rect = mRects[i];
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return i;
            }
        }
        return -1;
    }

    private Rect[] mRects;

    private void initRects() {
        mRects = new Rect[this.getChildCount()];
        for (int i = 0; i < this.getChildCount(); i++) {
            //拿到对应的子控件
            View childView = this.getChildAt(i);
            //创建与每个子控件对应的矩形对象
            Rect rect = new Rect(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
            mRects[i] = rect;
        }
    }


    @Override
    public boolean onLongClick(View v) {
        //被拖拽的View
        dragedView = v;

        //长按时,开始拖拽操作,显示出阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            v.startDragAndDrop(null, new View.DragShadowBuilder(v), null, 0);
            v.setEnabled(false);
        } else {
            v.startDrag(null, new View.DragShadowBuilder(v), null, 0);
            v.setEnabled(false);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (mOnDragItemClickListener != null) {
            mOnDragItemClickListener.onDragItemClick((TextView) v);
        }
    }

    private onDragItemClickListener mOnDragItemClickListener;

    public interface onDragItemClickListener {
        void onDragItemClick(TextView textView);
    }

    public void setOnDragItemClickListener(onDragItemClickListener onDragItemClickListener) {
        this.mOnDragItemClickListener = onDragItemClickListener;
    }
}
