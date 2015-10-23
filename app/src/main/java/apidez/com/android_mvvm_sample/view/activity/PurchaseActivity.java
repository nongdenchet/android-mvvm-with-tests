package apidez.com.android_mvvm_sample.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.MyApplication;
import apidez.com.android_mvvm_sample.utils.RxTextViewEx;
import apidez.com.android_mvvm_sample.utils.ToastUtils;
import apidez.com.android_mvvm_sample.utils.UiUtils;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nongdenchet on 10/1/15.
 */
public class PurchaseActivity extends BaseActivity {

    @Bind(R.id.creditCard)
    EditText mEdtCreditCard;

    @Bind(R.id.email)
    EditText mEdtEmail;

    @Bind(R.id.layoutCreditCard)
    TextInputLayout mLayoutCreditCard;

    @Bind(R.id.layoutEmail)
    TextInputLayout mLayoutEmail;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.btnSubmit)
    TextView mBtnSubmit;

    @Inject
    IPurchaseViewModel mViewModel;

    private ProgressDialog mProgressDialog;
    private View.OnClickListener onSubmitClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        // Setup dependency
        ((MyApplication) getApplication())
                .component()
                .inject(this);

        // Setup butterknife
        ButterKnife.bind(this);

        // Setup views
        setUpView();
    }

    private void setUpView() {
        // Progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);

        // Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindViewModel() {
        // binding credit card
        RxTextViewEx.textChanges(mEdtCreditCard)
                .takeUntil(preDestroy())
                .subscribe(mViewModel::nextCreditCard);

        // binding email
        RxTextViewEx.textChanges(mEdtEmail)
                .takeUntil(preDestroy())
                .subscribe(mViewModel::nextEmail);

        // create event on click on submit
        onSubmitClickListener = v -> mViewModel.submit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(preDestroy())
                .doOnSubscribe(mProgressDialog::show)
                .doOnTerminate(mProgressDialog::hide)
                .subscribe(done -> {
                    ToastUtils.showLongToast(getApplicationContext(), R.string.success);
                    finish();
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showLongToast(getApplicationContext(), R.string.error);
                });

        // binding credit card change
        mViewModel.creditCardValid()
                .takeUntil(preDestroy())
                .subscribe((enabled) -> {
                    mLayoutCreditCard.setError(getString(R.string.error_credit_card));
                    mLayoutCreditCard.setErrorEnabled(!enabled);
                    UiUtils.resetTintColor(this, mEdtCreditCard);
                });

        // binding password change
        mViewModel.emailValid()
                .takeUntil(preDestroy())
                .subscribe((enabled) -> {
                    mLayoutEmail.setError(getString(R.string.error_email));
                    mLayoutEmail.setErrorEnabled(!enabled);
                    UiUtils.resetTintColor(this, mEdtEmail);
                });

        // can submit
        mViewModel.canSubmit()
                .takeUntil(preDestroy())
                .subscribe(active -> {
                    mBtnSubmit.setBackgroundResource(active ? R.drawable.bg_submit : R.drawable.bg_inactive_submit);
                    mBtnSubmit.setOnClickListener(active ? onSubmitClickListener : null);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // bind to viewmodel
        bindViewModel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}