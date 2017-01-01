package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建RecyclerView类型的下拉控件
 * 参考： http://blog.csdn.net/xuehuayous/article/details/50387089
 * @author jayden
 * @date 2016-12-31
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView>{

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    @Override
    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    private boolean isFirstItemVisible() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();
        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if(null == adapter || adapter.getItemCount() == 0) {
            return true;
        } else {
            // 第一个条目完全展示,可以刷新
            if(getFirstVisiblePos() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView.getTop();
            }
        }
        return false;
    }

    private int getFirstVisiblePos() {
        View firstView = mRefreshableView.getChildAt(0);
        return firstView != null ? mRefreshableView.getChildAdapterPosition(firstView) : -1;
    }

    private boolean isLastItemVisible() {
        final Adapter<?> adapter = getRefreshableView().getAdapter();
        if(null == adapter || adapter.getItemCount() == 0) {
            return true;
        } else {
            if(getLastVisiblePos() >= mRefreshableView.getAdapter().getItemCount() - 1) {
                return mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1)
                        .getBottom() <= mRefreshableView.getBottom();
            }
        }
        return false;
    }

    private int getLastVisiblePos() {
        View lastView = mRefreshableView.getChildAt(mRefreshableView.getChildCount() - 1);
        return lastView != null ? mRefreshableView.getChildAdapterPosition(lastView) : -1;
    }
}
