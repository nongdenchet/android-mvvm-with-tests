package apidez.com.android_mvvm_sample.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.adapter.PlacesAdapter;
import apidez.com.android_mvvm_sample.application.DemoApplication;
import apidez.com.android_mvvm_sample.utils.ToastUtils;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlacesFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    IPlacesViewModel mViewModel;

    private ProgressDialog mProgressDialog;
    private PlacesAdapter mPlacesAdapter;

    public static PlacesFragment newInstance() {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PlacesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((DemoApplication) getActivityCompat().getApplication())
                .component()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, rootView);
        setupView();
        return rootView;
    }

    private void setupView() {
        // Progress dialog setup
        mProgressDialog = new ProgressDialog(getActivityCompat());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);

        // Actionbar setup
        getActivityCompat().setSupportActionBar(mToolbar);
        getActivityCompat().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recyclerview setup
        mPlacesAdapter = new PlacesAdapter(getActivity(), v -> {
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mPlacesAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observe current places
        mViewModel.currentPlaces()
                .takeUntil(preDestroy())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mPlacesAdapter::updatePlaces);

        // fetch all places
        mViewModel.fetchAllPlaces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .takeUntil(preDestroy())
                .doOnSubscribe(mProgressDialog::show)
                .doOnTerminate(mProgressDialog::hide)
                .subscribe(succes -> {
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showLongToast(getActivityCompat(), R.string.error);
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_places, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivityCompat().onBackPressed();
                return true;
            case R.id.action_cafe:
            case R.id.action_food:
            case R.id.action_store:
            case R.id.action_theater:
            case R.id.action_restaurant:
                // Filter the items
                mViewModel.filterPlacesByType(item.getTitle().toString().toLowerCase());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
