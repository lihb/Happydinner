package com.handgold.pjdc.ui.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by lihb on 15/10/14.
 */
public class CustomAnimation extends Animation {

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        t.getMatrix().setTranslate((float)(Math.sin(interpolatedTime *10)*20),0);
    }
}
