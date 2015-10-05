// Generated code from Butter Knife. Do not modify!
package apidez.com.android_mvvm_sample.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PurchaseActivity$$ViewBinder<T extends apidez.com.android_mvvm_sample.view.activity.PurchaseActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492970, "field 'edtCreditCard'");
    target.edtCreditCard = finder.castView(view, 2131492970, "field 'edtCreditCard'");
    view = finder.findRequiredView(source, 2131492972, "field 'edtEmail'");
    target.edtEmail = finder.castView(view, 2131492972, "field 'edtEmail'");
    view = finder.findRequiredView(source, 2131492969, "field 'layoutCreditCard'");
    target.layoutCreditCard = finder.castView(view, 2131492969, "field 'layoutCreditCard'");
    view = finder.findRequiredView(source, 2131492971, "field 'layoutEmail'");
    target.layoutEmail = finder.castView(view, 2131492971, "field 'layoutEmail'");
    view = finder.findRequiredView(source, 2131492966, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131492966, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131492973, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131492973, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.edtCreditCard = null;
    target.edtEmail = null;
    target.layoutCreditCard = null;
    target.layoutEmail = null;
    target.toolbar = null;
    target.btnSubmit = null;
  }
}
