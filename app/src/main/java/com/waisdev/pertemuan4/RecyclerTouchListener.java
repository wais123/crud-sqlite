package com.waisdev.pertemuan4;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private ClickListener clickListener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, RecyclerView recyclerView,ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(view != null && clickListener != null){
                    clickListener.onLongClick(view, recyclerView.getChildAdapterPosition(view));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(),e.getY());
        if(view != null && clickListener != null && gestureDetector.onTouchEvent(e)){
            clickListener.onClick(view, rv.getChildAdapterPosition(view));
        }
        return false;
    }

    @Override
    public void onTouchEvent( RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
