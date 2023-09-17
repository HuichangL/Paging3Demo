package com.example.paging3demo.paging

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("ClickableViewAccessibility")
fun RecyclerView.setAdapterWithDefaultFooter(adapter: MyPagingDataAdapter) {
    this.adapter = adapter.withLoadStateFooter(DefaultFooterAdapter())
    var downEventY = 0f
    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downEventY = event.rawY
            }

            MotionEvent.ACTION_UP -> {
                if (downEventY - event.rawY > 20) {
                    adapter.retry()
                }
            }
        }
        false
    }
}