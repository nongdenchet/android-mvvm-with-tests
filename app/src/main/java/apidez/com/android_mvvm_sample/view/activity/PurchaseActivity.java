package apidez.com.android_mvvm_sample.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trello.rxlifecycle.ActivityEvent;

import javax.inject.Inject;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.application.DemoApplication;
import apidez.com.android_mvvm_sample.util.RxTextViewEx;
import apidez.com.android_mvvm_sample.util.ToastUtils;
import apidez.com.android_mvvm_sample.util.UiUtils;
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
    EditText edtCreditCard;

    @Bind(R.id.email)
    EditText edtEmail;

    @Bind(R.id.layoutCreditCard)
    TextInputLayout layoutCreditCard;

    @Bind(R.id.layoutEmail)
    TextInputLayout layoutEmail;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.btnSubmit)
    TextView btnSubmit;

    @Inject
    IPurchaseViewModel viewModel;

    private ProgressDialog progressDialog;
    private View.OnClickListener onSubmitClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        // Setup dependency
        ((DemoApplication) getApplication()).component().inject(this);

        // Setup butterknife
        ButterKnife.bind(this);

        // Setup views
        setUpView();

        // bind to viewmodel
        bindViewModel();
    }

    private void setUpView() {
        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bindViewModel() {
        // binding credit card
        RxTextViewEx.textChanges(edtCreditCard)
                .compose(bindUntilEvent(ActivityEvent.STOP))
                .subscribe(viewModel::nextCreditCard);

        // binding email
        RxTextViewEx.textChanges(edtEmail)
                .compose(bindUntilEvent(ActivityEvent.STOP))
                .subscribe(viewModel::nextEmail);

        // create event on click on submit
        onSubmitClickListener = v -> viewModel.submit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(progressDialog::hide)
                .doOnSubscribe(progressDialog::show)
                .subscribe(done -> {
                    ToastUtils.showLongToast(getApplicationContext(), R.string.success);
                    finish();
                }, throwable -> {
                    ToastUtils.showLongToast(getApplicationContext(), R.string.error);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // binding credit card change
        viewModel.creditCardValid()
                .subscribe((enabled) -> {
                    layoutCreditCard.setError(getString(R.string.error_credit_card));
                    layoutCreditCard.setErrorEnabled(!enabled);
                    UiUtils.resetTintColor(this, edtCreditCard);
                });

        // binding password change
        viewModel.emailValid()
                .subscribe((enabled) -> {
                    layoutEmail.setError(getString(R.string.error_email));
                    layoutEmail.setErrorEnabled(!enabled);
                    UiUtils.resetTintColor(this, edtEmail);
                });

        // can submit
        viewModel.canSubmit()
                .subscribe(active -> {
                    btnSubmit.setBackgroundResource(active ? R.drawable.bg_submit : R.drawable.bg_inactive_submit);
                    btnSubmit.setOnClickListener(active ? onSubmitClickListener : null);
                });
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