package itstep.learning.android_212;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OnSwipeListener implements View.OnTouchListener {
    public void onSwipeBottom() { }   //
    public void onSwipeLeft()   { }   //
    public void onSwipeRight()  { }   //
    public void onSwipeTop()    { }   //


    private final GestureDetector gestureDetector;

    public OnSwipeListener( Context context ) {
        gestureDetector = new GestureDetector( context, new GestureListener() ) ;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch( View view, MotionEvent motionEvent ) {
        return gestureDetector.onTouchEvent( motionEvent );
    }


    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int minSwipeDistance = 100;
        private static final int minSwipeVelocity = 100;

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2,
                               float velocityX, float velocityY) {
            try {
                assert e1 != null;
                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                float mx = Math.abs(dx);
                float my = Math.abs(dy);

                if (mx > my) {
                    if (mx >= minSwipeDistance && Math.abs(velocityX) >= minSwipeVelocity) {
                        if (dx > 0) onSwipeRight();
                        else onSwipeLeft();
                        return true;
                    }
                } else {
                    if (my >= minSwipeDistance && Math.abs(velocityY) >= minSwipeVelocity) {
                        if (dy > 0) onSwipeBottom();
                        else onSwipeTop();
                        return true;
                    }
                }
            } catch (Exception ignored) {}
            return false;
        }
    }
}

