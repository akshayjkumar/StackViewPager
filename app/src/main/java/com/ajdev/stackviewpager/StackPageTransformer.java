package com.ajdev.stackviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

/**
 * Created by Akshay.Jayakumar.
 */

public class StackPageTransformer implements ViewPager.PageTransformer {

    private static final float DEFAULT_SCALE = 1f;
    private int dimen;
    private float cardElevationDefault = 10;
    private boolean leftToRight = true;
    private float offsetXDps = 27;
    private float offsetXPxs;
    /**
     * Default angle of rotation during swipe
     */
    public static final float DEFAULT_SWIPE_ROTATION = 30f;
    /**
     * Angle of rotation of page during swipe
     */
    private float mRotateDegrees = DEFAULT_SWIPE_ROTATION;

    public StackPageTransformer(Context context){
        /**
         * Convert DP into pixel. This converted offset value ensures that cards are displaced
         * by a value that that would occupy equal amount of pixels in any devices
         * irrespective of the screen density.
         */
        offsetXPxs = offsetXDps * context.getResources().getDisplayMetrics().density;
    }

    public void transformPage(View view, float position) {
        dimen = view.getWidth();
        view.setFocusable(false);
        if(leftToRight) {
            float transX = (-dimen * position) + (offsetXPxs * position);
            if (position < 0 && position >= -1) {
                if (mRotateDegrees > 0) {
                    float rotation = mRotateDegrees * position;
                    view.setRotation(rotation);
                }
                if(position == -1){
                    /**
                     * Hide the page that is to the left. This media card is single direction.
                     */
                    view.setAlpha(0f);
                }
            } else if (position == 0) {
                view.setFocusable(true);
                view.setAlpha(1.0f);
                ((CardView) view).setCardElevation(cardElevationDefault);
                view.setTranslationX(transX);
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
                view.setRotation(0);
            } else if (position > 0) {
                float ceil = (float) round((float) Math.ceil(Math.abs(position)), 1);
                float floor = (float) round((float) Math.floor(Math.abs(position)), 1);
                float scaleCeil = 0, newFloor = 0, scale = 0;
                if (position == ceil && position == floor) {
                    // This means the position is whole number eg: 1,2,3
                    float floorTemp = (float) (floor - 1);
                    newFloor = (float) round((float) (floorTemp + 0.1), 1);
                } else
                    newFloor = (float) round((float) (floor + 0.1), 1);

                float scaleTolerance = (float) round(ceil / 10, 1);
                float higher = (float) round(DEFAULT_SCALE - scaleTolerance ,1);
                float lower = (float) round(higher - 0.1f ,1);

                /**
                 *  NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin
                 *  Here, when the page moves from right to left, the scale range is inverted. Hence (lower - higher).
                 */
                scale = (((Math.abs(position) - Math.abs(newFloor)) * (lower - higher)) / (Math.abs(ceil) - Math.abs(newFloor))) + higher;
                ((CardView) view).setCardElevation(cardElevationDefault - ceil);
                float adjustedScale = (scale <= higher) ? scale : higher;
                view.setScaleX(adjustedScale);
                view.setScaleY((scale <= higher) ? scale : higher);
                view.setTranslationX(transX);
                if(position <  5)
                    view.setAlpha(1.0f);
                else
                    view.setAlpha(0f);
            }else{
                view.setAlpha(0f);
            }
        }else{
            // Reverse logic of the above can be implemented for RTL support
        }
    }

    private static double round (float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
}
