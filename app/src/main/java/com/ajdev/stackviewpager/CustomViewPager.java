package com.ajdev.stackviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by Akshay.Jayakumar.
 *
 * This is a custom implementation of Viewpager with custom scroller.
 * Custom scroller will be used to override default.
 *
 * This custom implementation of the Viewpager also has the functionality to restrict
 * swiping to a  particular direction.
 *
 * This custom viewpager is used in Home to show all tabs nad fragments and also
 * is used in Media cards
 */

public class CustomViewPager extends ViewPager {

    public static final int SwipeDirection_RIGHT = 1;
    public static final int SwipeDirection_LEFT = 2;
    public static final int SwipeDirection_ALL = 0;
    public static final int SwipeDirection_NONE = -1;
    public static final int SCROLL_MODE_DEFAULT = 250;
    public static final int SCROLL_MODE_MEDIUM = 750;
    public static final int SCROLL_MODE_SLOW = 1000;
    public static final int SCROLL_MODE_ULTRA_SLOW = 2000;

    private float initialXValue;
    private int direction;
    private CustomScroller ownScroller;

    public CustomViewPager(Context context) {
        super(context);
        this.direction = SwipeDirection_ALL;
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection_ALL;
    }

    public void setScrollDuration(int millis) {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            WeakReference<Context> wr = new WeakReference<>(getContext());
            ownScroller = new CustomScroller(wr.get(), millis);
            scroller.set(this, ownScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            try {
                return super.onInterceptTouchEvent(event);
            }catch (Exception e){
                return false;
            }
        }

        return false;
    }

    private boolean IsSwipeAllowed(MotionEvent event) {
        if(this.direction == SwipeDirection_ALL) return true;

        if(direction == SwipeDirection_NONE )//disable any swipe
            return false;

        if(event.getAction()== MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if(event.getAction()== MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection_RIGHT ) {
                    // swipe from left to right detected
                    return false;
                }else if (diffX < 0 && direction == SwipeDirection_LEFT ) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setSwipeDirection(int direction) {
        this.direction = direction;
    }

}