package com.handgold.pjdc.ui.widget;

import android.animation.TypeEvaluator;

/**
 * Created by lihb on 15/10/26.
 */
public class AddToCartIntEvaluator implements TypeEvaluator<Integer> {

    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int result;
        if (fraction <= 0.5f) {
            result = (int)(startValue - (fraction * (endValue - startValue)));
        }else {
            result = (int)(startValue + (fraction * (endValue - startValue)));
        }
        return result;
    }
}
