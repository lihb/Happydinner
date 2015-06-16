// Generated code from Butter Knife. Do not modify!
package com.happydinner.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FoodShowActivity$$ViewInjector<T extends com.happydinner.activity.FoodShowActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361893, "field 'mMenuListview'");
    target.mMenuListview = finder.castView(view, 2131361893, "field 'mMenuListview'");
    view = finder.findRequiredView(source, 2131361895, "field 'menuTitleTv'");
    target.menuTitleTv = finder.castView(view, 2131361895, "field 'menuTitleTv'");
    view = finder.findRequiredView(source, 2131361896, "field 'menuImageIv'");
    target.menuImageIv = finder.castView(view, 2131361896, "field 'menuImageIv'");
    view = finder.findRequiredView(source, 2131361897, "field 'menuDescTv'");
    target.menuDescTv = finder.castView(view, 2131361897, "field 'menuDescTv'");
    view = finder.findRequiredView(source, 2131361898, "field 'menuAddToOrderTvDesc'");
    target.menuAddToOrderTvDesc = finder.castView(view, 2131361898, "field 'menuAddToOrderTvDesc'");
    view = finder.findRequiredView(source, 2131361894, "field 'menuDescRl'");
    target.menuDescRl = finder.castView(view, 2131361894, "field 'menuDescRl'");
    view = finder.findRequiredView(source, 2131361900, "field 'descMenuCountSubTv'");
    target.descMenuCountSubTv = finder.castView(view, 2131361900, "field 'descMenuCountSubTv'");
    view = finder.findRequiredView(source, 2131361901, "field 'descMenuCountConfirmTv'");
    target.descMenuCountConfirmTv = finder.castView(view, 2131361901, "field 'descMenuCountConfirmTv'");
    view = finder.findRequiredView(source, 2131361902, "field 'descMenuCountAddTv'");
    target.descMenuCountAddTv = finder.castView(view, 2131361902, "field 'descMenuCountAddTv'");
    view = finder.findRequiredView(source, 2131361899, "field 'descMenuCountLl'");
    target.descMenuCountLl = finder.castView(view, 2131361899, "field 'descMenuCountLl'");
  }

  @Override public void reset(T target) {
    target.mMenuListview = null;
    target.menuTitleTv = null;
    target.menuImageIv = null;
    target.menuDescTv = null;
    target.menuAddToOrderTvDesc = null;
    target.menuDescRl = null;
    target.descMenuCountSubTv = null;
    target.descMenuCountConfirmTv = null;
    target.descMenuCountAddTv = null;
    target.descMenuCountLl = null;
  }
}
