package com.example.recyclerviewtest;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StarDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "StarDecoration";

    private int groupHeaderHeight;

    private Paint headPaint;
    private Paint textPaint;

    private Rect textRect;

    private Context context;

    public StarDecoration(Context context) {
        this.context = context;
        groupHeaderHeight = dp2px(context, 100);

        headPaint = new Paint();
        headPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(Color.WHITE);

        textRect = new Rect();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (parent.getAdapter() instanceof MyAdapter){
            MyAdapter adapter = (MyAdapter) parent.getAdapter();
            // 当前屏幕的item个数 - 看得见的item
            int count = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < count; i++){
                // 获取对应i的view
                View view = parent.getChildAt(i);
                // 获取view的布局位置
                int position = parent.getChildLayoutPosition(view);
//                Log.d(TAG, "onDraw - position  =========================== "+position);
                // 是否是头部
                boolean isGroupHeader = adapter.isGroupHeader(position);
                if (isGroupHeader && view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0 ){
                    c.drawRect(left, view.getTop() - groupHeaderHeight, right, view.getTop(), headPaint);
                    // 绘制文字
                    String groupName = adapter.getGroupName(position);
                    textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                    c.drawText(groupName, left+20, view.getTop() - groupHeaderHeight/2 + textRect.height()/2, textPaint);

                } else if (view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0){
                    // 分割线
                    c.drawRect(left, view.getTop() - 4, right, view.getTop(), headPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof MyAdapter){
            MyAdapter adapter = (MyAdapter) parent.getAdapter();
            // 返回可见区域的第一个item的position
            int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            // 获取对应position的view
            View itemView = parent.findViewHolderForAdapterPosition(position).itemView;
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            // 当第2个是组的头部
            boolean isGroupHeader = adapter.isGroupHeader(position + 1);
            if (isGroupHeader) {
                int bottom = Math.min(groupHeaderHeight, itemView.getBottom() - parent.getPaddingTop());

                c.drawRect(left, top, right, top + bottom, headPaint);
                // 绘制文字
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);

                // 文字不能超过区域
                // 当设置padding，没下面这个行代码，就会在padding区域显示文字了
                // 可以尝试注释掉这行，体验下效果
                c.clipRect(left, top, right, top + bottom);

                c.drawText(groupName, left+20, top + bottom - groupHeaderHeight/2 + textRect.height()/2, textPaint);
            } else {
                c.drawRect(left, top, right, top + groupHeaderHeight, headPaint);
                // 绘制文字
                String groupName = adapter.getGroupName(position);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left+20, top + groupHeaderHeight/2 + textRect.height()/2, textPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() instanceof MyAdapter){
            MyAdapter adapter = (MyAdapter) parent.getAdapter();
            int position = parent.getChildLayoutPosition(view);
            boolean isGroupHeader = adapter.isGroupHeader(position);
            if (isGroupHeader) {
                // 如果是头部，预留更大的地方
                outRect.set(0, groupHeaderHeight, 0 , 0);
            } else {
                outRect.set(0, 4, 0, 0);
            }
        }
    }

    // dp 转成 px
    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale * 0.5f);
    }
}
