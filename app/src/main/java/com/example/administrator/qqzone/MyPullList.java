package com.example.administrator.qqzone;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class MyPullList extends ListView {

    private ImageView mIv;
    private int mImageViewHeigth = 0;

    public MyPullList(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mImageViewHeigth = context.getResources().getDimensionPixelOffset(R.dimen.head_img_defualt_size_dp);
        Log.e("MyPullList", "mImageViewHeigth:" + mImageViewHeigth);
    }


    public void setImageBig(ImageView iv) {
        mIv = iv;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //让Imageview上滑时放大监听
        View head = (View) mIv.getParent();
        //拿到父容器与顶部的高度
        if (head.getTop() < 0 && mIv.getHeight() > mImageViewHeigth) {
            mIv.getLayoutParams().height = mIv.getHeight() + head.getTop();
            head.layout(head.getLeft(), 0, head.getRight(), head.getHeight());
            mIv.requestLayout();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                ResetAnimation animation = new ResetAnimation(mIv, mImageViewHeigth);
                animation.setDuration(300);
                mIv.startAnimation(animation);

                break;
        }

        return super.onTouchEvent(ev);
    }

    class ResetAnimation extends Animation {

        private ImageView iv;
        private int targetHeigth; //最终恢复的高度
        private final int height;
        private final int endHeigth;

        public ResetAnimation(ImageView iv, int targetHeigth) {
            this.iv = iv;
            this.targetHeigth = targetHeigth;
            this.height = mIv.getHeight();
            this.endHeigth = height - targetHeigth;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            //不断调用
            mIv.getLayoutParams().height = (int) (height - endHeigth * interpolatedTime);
            mIv.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0) {
            mIv.getLayoutParams().height = mIv.getHeight() - deltaY / 2;
            mIv.requestLayout();
        } else {
            //缩小
            if (mIv.getHeight() > mImageViewHeigth) {
                mIv.getLayoutParams().height = mIv.getHeight() + deltaY / 2;
                mIv.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
