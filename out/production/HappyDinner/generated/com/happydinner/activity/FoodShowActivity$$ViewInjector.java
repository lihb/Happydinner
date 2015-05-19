// Generated code from Butter Knife. Do not modify!
package com.happydinner.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FoodShowActivity$$ViewInjector<T extends com.happydinner.activity.FoodShowActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361877, "field 'mMenuListview'");
    target.mMenuListview = finder.castView(view, 2131361877, "field 'mMenuListview'");
  }

  @Override public void reset(T target) {
    target.mMenuListview = null;
  }
}
