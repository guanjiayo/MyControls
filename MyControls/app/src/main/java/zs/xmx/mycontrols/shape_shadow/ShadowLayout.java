package zs.xmx.mycontrols.shape_shadow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import zs.xmx.mycontrols.R;


/**
 * GITHUB:https://github.com/lihangleo2/ShadowLayout
 * 中文文档：https://juejin.im/post/5d4c1392f265da03bc126584#heading-11
 * 只保留了阴影的处理处分代码
 */
public class ShadowLayout extends FrameLayout {

    private int     mBackGroundColor;
    private int     mShadowColor;
    private float   mShadowLimit;
    private float   mCornerRadius;
    private float   mDx;
    private float   mDy;
    private boolean leftShow;
    private boolean rightShow;
    private boolean topShow;
    private boolean bottomShow;
    private Paint   shadowPaint;
    private Paint   paint;

    private int   leftPading;
    private int   topPading;
    private int   rightPading;
    private int   bottomPading;
    //阴影布局子空间区域
    private RectF rectf = new RectF();

    private boolean isSym;


    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    //动态设置x轴偏移量
    public void setShadowOffsetX(float mDx) {
        if (Math.abs(mDx) > mShadowLimit) {
            if (mDx > 0) {
                this.mDx = mShadowLimit;
            } else {
                this.mDx = -mShadowLimit;
            }
        } else {
            this.mDx = mDx;
        }
        setPadding();
    }

    //动态设置y轴偏移量
    public void setShadowOffsetY(float mDy) {
        if (Math.abs(mDy) > mShadowLimit) {
            if (mDy > 0) {
                this.mDy = mShadowLimit;
            } else {
                this.mDy = -mShadowLimit;
            }
        } else {
            this.mDy = mDy;
        }
        setPadding();
    }


    public float getCornerRadius() {
        return mCornerRadius;
    }

    //动态设置 圆角属性
    public void setCornerRadius(int mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
    }

    public float getShadowLimit() {
        return mShadowLimit;
    }

    //动态设置阴影扩散区域
    public void setShadowLimit(int mShadowLimit) {
        this.mShadowLimit = mShadowLimit;
        setPadding();
    }

    //动态设置阴影颜色值
    public void setShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
    }


    //是否隐藏阴影的上边部分
    public void setShadowHiddenTop(boolean topShow) {
        this.topShow = !topShow;
        setPadding();
    }

    public void setShadowHiddenBottom(boolean bottomShow) {
        this.bottomShow = !bottomShow;
        setPadding();
    }


    public void setShadowHiddenRight(boolean rightShow) {
        this.rightShow = !rightShow;
        setPadding();
    }


    public void setShadowHiddenLeft(boolean leftShow) {
        this.leftShow = !leftShow;
        setPadding();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
        }
    }

    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);


        //矩形画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mBackGroundColor);

        setPadding();
    }


    public void setPadding() {
        //控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走
        if (isSym) {
            int xPadding = (int) (mShadowLimit + Math.abs(mDx));
            int yPadding = (int) (mShadowLimit + Math.abs(mDy));

            if (leftShow) {
                leftPading = xPadding;
            } else {
                leftPading = 0;
            }

            if (topShow) {
                topPading = yPadding;
            } else {
                topPading = 0;
            }


            if (rightShow) {
                rightPading = xPadding;
            } else {
                rightPading = 0;
            }

            if (bottomShow) {
                bottomPading = yPadding;
            } else {
                bottomPading = 0;
            }
        } else {
            if (Math.abs(mDy) > mShadowLimit) {
                if (mDy > 0) {
                    mDy = mShadowLimit;
                } else {
                    mDy = 0 - mShadowLimit;
                }
            }


            if (Math.abs(mDx) > mShadowLimit) {
                if (mDx > 0) {
                    mDx = mShadowLimit;
                } else {
                    mDx = 0 - mShadowLimit;
                }
            }

            if (topShow) {
                topPading = (int) (mShadowLimit - mDy);
            } else {
                topPading = 0;
            }

            if (bottomShow) {
                bottomPading = (int) (mShadowLimit + mDy);
            } else {
                bottomPading = 0;
            }


            if (rightShow) {
                rightPading = (int) (mShadowLimit - mDx);
            } else {
                rightPading = 0;
            }


            if (leftShow) {
                leftPading = (int) (mShadowLimit + mDx);
            } else {
                leftPading = 0;
            }
        }


        setPadding(leftPading, topPading, rightPading, bottomPading);
    }


    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        //判断传入的颜色值是否有透明度
        isAddAlpha(mShadowColor);
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowLimit, mDx, mDy, mShadowColor, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }


    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }

        try {
            //默认是显示
            leftShow = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenLeft, true);
            rightShow = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenRight, true);
            bottomShow = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenBottom, true);
            topShow = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenTop, true);
            mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius, getResources().getDimension(R.dimen.dp_0));
            //默认扩散区域宽度
            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, getResources().getDimension(R.dimen.dp_5));

            //x轴偏移量
            mDx = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetX, 0);
            //y轴偏移量
            mDy = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetY, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowColor, getResources().getColor(R.color.default_shadow_color));
            mBackGroundColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowBackColor, getResources().getColor(R.color.default_shadowback_color));
            isSym = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowSymmetry, true);
        } finally {
            attr.recycle();
        }
    }


    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {
        //优化阴影bitmap大小,将尺寸缩小至原来的1/4。
        dx = dx / 4;
        dy = dy / 4;
        shadowWidth = shadowWidth / 4;
        shadowHeight = shadowHeight / 4;
        cornerRadius = cornerRadius / 4;
        shadowRadius = shadowRadius / 4;

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);

        if (isSym) {
            if (dy > 0) {
                shadowRect.top += dy;
                shadowRect.bottom -= dy;
            } else if (dy < 0) {
                shadowRect.top += Math.abs(dy);
                shadowRect.bottom -= Math.abs(dy);
            }

            if (dx > 0) {
                shadowRect.left += dx;
                shadowRect.right -= dx;
            } else if (dx < 0) {

                shadowRect.left += Math.abs(dx);
                shadowRect.right -= Math.abs(dx);
            }
        } else {
            shadowRect.top -= dy;
            shadowRect.bottom -= dy;
            shadowRect.right -= dx;
            shadowRect.left -= dx;
        }


        shadowPaint.setColor(fillColor);
        if (!isInEditMode()) {//dx  dy
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectf.left = leftPading;
        rectf.top = topPading;
        rectf.right = getWidth() - rightPading;
        rectf.bottom = getHeight() - bottomPading;
        int trueHeight = (int) (rectf.bottom - rectf.top);

        if (mCornerRadius > trueHeight / 2) {
            //画圆角矩形
            canvas.drawRoundRect(rectf, trueHeight / 2, trueHeight / 2, paint);
//            canvas.drawRoundRect(rectf, trueHeight / 2, trueHeight / 2, paintStroke);
        } else {
            canvas.drawRoundRect(rectf, mCornerRadius, mCornerRadius, paint);
//            canvas.drawRoundRect(rectf, mCornerRadius, mCornerRadius, paintStroke);
        }
    }


    public void isAddAlpha(int color) {
        //获取单签颜色值的透明度，如果没有设置透明度，默认加上#2a
        if (Color.alpha(color) == 255) {
            String red = Integer.toHexString(Color.red(color));
            String green = Integer.toHexString(Color.green(color));
            String blue = Integer.toHexString(Color.blue(color));

            if (red.length() == 1) {
                red = "0" + red;
            }

            if (green.length() == 1) {
                green = "0" + green;
            }

            if (blue.length() == 1) {
                blue = "0" + blue;
            }
            String endColor = "#2a" + red + green + blue;
            mShadowColor = convertToColorInt(endColor);
        }
    }


    public static int convertToColorInt(String argb)
            throws IllegalArgumentException {

        if (!argb.startsWith("#")) {
            argb = "#" + argb;
        }

        return Color.parseColor(argb);
    }


}