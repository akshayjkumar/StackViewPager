package com.ajdev.stackviewpager;

import android.content.Context;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by Akshay.Jayakumar.
 *
 * This is a custom scroller which encapsulates scrolling. Provides data for scrolling
 * animation in response to the fling gesture of the viewpager. DurationScrollMillis
 * defines the duration for scroll animation.
 */

public class CustomScroller extends Scroller {

    private int durationScrollMillis = 1;

    public CustomScroller(Context context, int durationScroll) {
        super(context, new DecelerateInterpolator());
        this.durationScrollMillis = durationScroll;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, durationScrollMillis);
    }

}
