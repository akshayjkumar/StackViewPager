package com.ajdev.stackviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CustomViewPager cViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cViewPager = (CustomViewPager) findViewById(R.id.cViewPager);
        /**
         * Set the scroll animation duration of the custom view pager. Check the CustomScroller for
         * more information.
        **/
        cViewPager.setScrollDuration(CustomViewPager.SCROLL_MODE_DEFAULT);
        // Prevent from showing over scroll animation
        cViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // Change the default page transform property to the stack page transform.
        cViewPager.setPageTransformer(false, new StackPageTransformer(getBaseContext()));
        // Allow swiping only from right to left.
        cViewPager.setSwipeDirection(CustomViewPager.SwipeDirection_RIGHT);
        /**
         * Set an optimal value for the number of pages that should be retained
         * on the either sides of a given page. This helps the StackPagerTransformer
         * to provide animation that resembles a stack of pages
         */
        cViewPager.setOffscreenPageLimit(3);
        // Load the adapter to the view pager
        PagerDataAdapter pagerDataAdapter = new PagerDataAdapter(getBaseContext());
        cViewPager.setAdapter(pagerDataAdapter);
    }
}
