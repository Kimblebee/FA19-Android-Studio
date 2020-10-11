package com.example.myproject5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,     GestureDetector.OnDoubleTapListener


{
    ListFragment lFrag;
    ImageFragment iFrag;
    GestureDetector gd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // putting the fragments into their containers
        lFrag = new ListFragment();
        iFrag = new ImageFragment();
        gd = new GestureDetector(this);





        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listContainer, lFrag)
                .replace(R.id.imgContainer, iFrag).commit();


        LinearLayout linear = findViewById(R.id.linLay);
        linear.setOnTouchListener(new ConstraintLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //handleUserTouchEvents(motionEvent);
                //Return false means that we will pass the touch event on.
                //Return true means that we have handled the touch event and it is not passed on.
                return false;
            }
        });

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        //oof
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        //lFrag.update("You went down");

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        //lFrag.update("You pressed");


    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        lFrag.update("You single tapped");

        return true;

    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
       // lFrag.update("You scrolled");

        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
       // lFrag.update("You long pressed");

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        float diffY = motionEvent1.getY() - motionEvent.getY();
        float diffX = motionEvent1.getX() - motionEvent.getX();
        boolean result = false;

        // handling left and right swipes
        if (Math.abs(diffX) > Math.abs(diffY)){
            // checking to see if you swiped properly
            if (Math.abs(diffX) > 100 && Math.abs(v) > 100){
                if (diffX > 0){
                    onSwipeRight();
                    result = true;
                }
                else{
                    onSwipeLeft();
                    result = true;
                }
            }
        }
        else {
            // handling up and down swipes

                // checking to see if you swiped properly
                if (Math.abs(diffY) > 100 && Math.abs(v1) > 100) {
                    if (diffY > 0) {
                        onSwipeDown();
                        result = true;
                    } else {
                        onSwipeUp();
                        result = true;
                    }

            }
        }

        return result;
    }



    // based on swipe direction,add to the log and move the ball

    public void onSwipeDown(){
        iFrag.changeImage("down");
        lFrag.swipe("down");


    }

    private void onSwipeUp() {
        iFrag.changeImage("up");
        lFrag.swipe("up");



    }


    private void onSwipeLeft() {
        iFrag.changeImage("left");
        lFrag.swipe("left");



    }

    private void onSwipeRight() {
        iFrag.changeImage("right");
        lFrag.swipe("right");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        lFrag.update("You single tapped");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        lFrag.update("You double tapped");

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        //lFrag.update("You double tapped");

        return false;
    }
}
