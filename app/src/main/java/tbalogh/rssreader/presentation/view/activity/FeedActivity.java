package tbalogh.rssreader.presentation.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tbalogh.rssreader.R;
import tbalogh.rssreader.presentation.RssReaderApplication;
import tbalogh.rssreader.presentation.di.component.ApplicationComponent;
import tbalogh.rssreader.presentation.di.component.DaggerFeedComponent;
import tbalogh.rssreader.presentation.di.component.FeedComponent;
import tbalogh.rssreader.presentation.di.module.FeedModule;
import tbalogh.rssreader.presentation.model.FeedItemModel;
import tbalogh.rssreader.presentation.model.FeedModel;
import tbalogh.rssreader.presentation.view.FeedView;
import tbalogh.rssreader.presentation.view.OnFeedItemClickedListener;
import tbalogh.rssreader.presentation.view.adapter.DividerItemDecoration;
import tbalogh.rssreader.presentation.view.adapter.FeedAdapter;
import tbalogh.rssreader.presentation.view.presenter.FeedPresenter;
import tbalogh.rssreader.presentation.view.util.LocalizedStringProvider;

/**
 * Created by tbalogh on 27/07/16.
 */
public class FeedActivity extends AppCompatActivity implements FeedView, OnFeedItemClickedListener {

    public static final String RETRY = "Retry";

    @BindView(R.id.layout_root)         View               rootLayout;
    @BindView(R.id.layout_swipe_reresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_feed)             RecyclerView       feedListView;
    @BindView(R.id.toolbar)             Toolbar            toolbar;
    @BindView(R.id.tv_phandroid)        TextView           feedTitleTextView;
    @BindView(R.id.ic_back)             View               backView;
    @BindView(R.id.ic_clear_search)     View               cancelSearchView;
    @BindView(R.id.et_search)           EditText           searchEditText;
    @BindView(R.id.ic_search)           View               searchView;

    @Inject FeedPresenter           feedPresenter;
    @Inject LocalizedStringProvider localizedStringProvider;

    private FeedComponent  feedComponent;
    private FeedAdapter    feedAdapter;
    private ProgressDialog progressDialog;

    private String category = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
        initializeInjector();
        this.feedComponent.inject(this);
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.feedPresenter.setFeedView(this);
        this.feedPresenter.onResume();
        this.feedAdapter.setOnFeedItemClickedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.feedPresenter.onPause();
        this.feedPresenter.removeFeedView();
        this.feedAdapter.removeFeedItemClickedListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.feedPresenter.onDestroy();
    }

    @Override
    public void showFeed(FeedModel feed) {
        this.feedAdapter.update(feed.getFeedItems());
    }

    @Override
    public void showNoMatchingCategory() {
        Snackbar.make(this.rootLayout, localizedStringProvider.noMatchingCategory(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showInternetConnectionError() {
        Snackbar snackbar = Snackbar.make(
                this.rootLayout,
                localizedStringProvider.internetConnectionError(),
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(RETRY, v -> this.feedPresenter.getFeed(this.category));
        snackbar.show();
    }

    @Override
    public void showRequestTimeoutError() {
        Snackbar snackbar = Snackbar.make(
                this.rootLayout,
                localizedStringProvider.requestTimeout(),
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(RETRY, v -> this.feedPresenter.getFeed(this.category));
        snackbar.show();
    }

    @Override
    public void showError() {
        Snackbar.make(this.rootLayout, localizedStringProvider.somethingWentWrong(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public String getFilterTag() {
        return category;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.ic_search)
    public void enterSearchMode() {
        showSearchViews();
        if (this.searchEditText.requestFocus()) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this.searchEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @OnClick(R.id.ic_back)
    public void leaveSearchMode() {
        clearSearchText();
        hideSearchViews();
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.searchEditText.getWindowToken(), 0);
    }

    @OnClick(R.id.ic_clear_search)
    public void clearSearchText() {
        if (!this.searchEditText.getText().toString().isEmpty()) {
            this.searchEditText.setText("");
            this.category = "";
            this.feedPresenter.filterFeed(this.category);
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setMessage(localizedStringProvider.loading());
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            this.progressDialog.setIndeterminate(true);
        }
        this.progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        this.swipeRefreshLayout.setRefreshing(false);
    }

    private void showSearchViews() {
        this.searchEditText.setVisibility(View.VISIBLE);
        this.backView.setVisibility(View.VISIBLE);
        this.cancelSearchView.setVisibility(View.VISIBLE);
        this.feedTitleTextView.setVisibility(View.GONE);
        this.searchView.setVisibility(View.GONE);
    }

    private void hideSearchViews() {
        this.searchEditText.setVisibility(View.GONE);
        this.backView.setVisibility(View.GONE);
        this.cancelSearchView.setVisibility(View.GONE);
        this.feedTitleTextView.setVisibility(View.VISIBLE);
        this.searchView.setVisibility(View.VISIBLE);
    }

    private void setupViews() {
        setSupportActionBar(this.toolbar);
        setupRecyclerView();
        setupSearchEditText();
        this.swipeRefreshLayout.setOnRefreshListener(
                () -> this.feedPresenter.getFeed(this.category));
    }

    private void setupSearchEditText() {
        this.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                this.category = this.searchEditText.getText().toString();
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                this.searchEditText.clearFocus();
                this.feedPresenter.filterFeed(category);

                return true;
            }
            return false;
        });
    }

    private void setupRecyclerView() {
        this.feedListView.setLayoutManager(new LinearLayoutManager(this));
        this.feedListView.addItemDecoration(new DividerItemDecoration(this));
        this.feedAdapter = new FeedAdapter(this);
        this.feedListView.setAdapter(this.feedAdapter);
    }

    private void initializeInjector() {
        ApplicationComponent component = ((RssReaderApplication) getApplication()).getApplicationComponent();
        this.feedComponent = DaggerFeedComponent.builder()
                                                .applicationComponent(component)
                                                .feedModule(new FeedModule())
                                                .build();
    }

    @Override
    public void onFeedItemClicked(FeedItemModel feedModel) {
        this.feedPresenter.openFeed(feedModel);
    }
}
