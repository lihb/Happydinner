package com.handgold.pjdc.ui.widget;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by lihb on 15/10/26.
 */
public class AddToCartIntEvaluator implements TypeEvaluator<Object> {


    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        MyPoint myPoint = null;
        MyPoint startPoint = (MyPoint) startValue;
        MyPoint endPoint = (MyPoint) endValue;
        float x, y;

//        x = 200 * fraction * 3;
//        y = 0.5f * 100 * (fraction * 3) * (fraction * 3);
      /*  double a = 2,b,c;
        b = ((endPoint.getY() - startPoint.getY()) - a * (Math.pow(endPoint.getX(), 2) - Math.pow(startPoint.getX(), 2)))/(endPoint.getX() - startPoint.getX());
        c = startPoint.getY() - a * Math.pow(startPoint.getX(), 2) - b * startPoint.getX();
        Log.i("wwww", "a = " + a + ", b = " + b + ", c = " + c);

        x = (int)(startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX()));

        y = (int)(a * Math.pow(x, 2) + b * x + c);*/

        float t = fraction * 5;
        float a = (float)Math.sqrt(2 * (endPoint.getY() - startPoint.getY() / t));

        x = (startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX()));
        Log.i("wwww", "fraction = " + fraction);
        y = 0.5f * a * t * t;

        myPoint = new MyPoint(x, y);

        return myPoint;
    }
}
