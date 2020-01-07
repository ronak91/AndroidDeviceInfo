package com.FiftyOneDegree.research.RecycleDragDrop;


import androidx.recyclerview.widget.RecyclerView;

public interface StartDragListener {
    void requestDrag(RecyclerView.ViewHolder viewHolder);
}