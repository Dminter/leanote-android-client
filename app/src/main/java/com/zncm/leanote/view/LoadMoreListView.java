package com.zncm.leanote.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zncm.leanote.R;


public class LoadMoreListView extends ListView implements OnScrollListener {
    private static final String TAG = "LoadMoreListView";
    private OnScrollListener mOnScrollListener;
    private LayoutInflater mInflater;
    private RelativeLayout mFooterView;
    private TextView mLabLoadMore;
    private ProgressBar mProgressBarLoadMore;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mIsLoadingMore = false;
    private boolean mCanLoadMore = true;
    private int mCurrentScrollState;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooterView = (RelativeLayout) mInflater.inflate(
                R.layout.load_more_footer, this, false);
        mLabLoadMore = (TextView) mFooterView.findViewById(R.id.no_more_textView);
        mProgressBarLoadMore = (ProgressBar) mFooterView
                .findViewById(R.id.load_more_progressBar);
        addFooterView(mFooterView);
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mOnScrollListener = l;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    @SuppressLint("ShowToast")
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
        if (mOnLoadMoreListener != null) {
            if (visibleItemCount == totalItemCount) {
                mProgressBarLoadMore.setVisibility(View.GONE);
                mLabLoadMore.setVisibility(View.GONE);
                return;
            }
            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
            if (!mIsLoadingMore && loadMore
                    && mCurrentScrollState != SCROLL_STATE_IDLE) {
                if (!mCanLoadMore) {
                    mLabLoadMore.setVisibility(View.VISIBLE);
                    return;
                }
                mProgressBarLoadMore.setVisibility(View.VISIBLE);
                mLabLoadMore.setVisibility(View.INVISIBLE);
                mIsLoadingMore = true;
                onLoadMore();
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    public void setCanLoadMore(boolean canLoadMore) {
        mCanLoadMore = canLoadMore;
        mLabLoadMore.setVisibility(View.INVISIBLE);
    }

    public void onLoadMore() {
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
        mProgressBarLoadMore.setVisibility(View.GONE);
    }


    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

}