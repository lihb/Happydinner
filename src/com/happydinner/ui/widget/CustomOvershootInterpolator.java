package com.happydinner.ui.widget;

import android.view.animation.OvershootInterpolator;

/**
 * Created by Administrator on 2015/10/14.
 */
public class CustomOvershootInterpolator extends OvershootInterpolator {

    @Override
    public float getInterpolation(float t) {
        float result = 0.01f;
        if (t <= 0.2) {
            System.out.println(" 5x====" + 5 * t);
            result = 5 * t;
        }else if (t <= 0.6) {
            result = (float)(Math.sin(2.5*Math.PI * t - 0.5*Math.PI)+0.3f);
            System.out.println("Math.sin(2.5f*Math.PI * t - 0.5f*Math.PI)+0.6f====" + result);
        }else {
            result = (float)(-Math.sin(2.5*Math.PI * t - 0.5*Math.PI)+0.3f);
            System.out.println("-Math.sin(2.5f*Math.PI * t - 0.5f*Math.PI)+0.6f====" + result);
        }
        return result;
    }
}
