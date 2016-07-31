package tbalogh.rssreader.presentation.view.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import tbalogh.rssreader.domain.Feed;
import tbalogh.rssreader.domain.interactor.FilterFeedInteractor;
import tbalogh.rssreader.domain.interactor.GetFeedInteractor;
import tbalogh.rssreader.presentation.di.PerActivity;
import tbalogh.rssreader.presentation.mapper.FeedModelMapper;
import tbalogh.rssreader.presentation.model.FeedItemModel;
import tbalogh.rssreader.presentation.model.FeedModel;
import tbalogh.rssreader.presentation.view.FeedView;

/**
 * Created by tbalogh on 27/07/16.
 */
@PerActivity
public class FeedPresenter implements Presenter {

    private final FeedModelMapper      feedModelMapper;
    private final GetFeedInteractor    getFeed;
    private final FilterFeedInteractor filterFeed;

    private FeedView feedView;

    @Inject
    public FeedPresenter(GetFeedInteractor feedInteractor, FilterFeedInteractor filterFeed,
                         FeedModelMapper feedModelMapper) {
        this.getFeed = feedInteractor;
        this.filterFeed = filterFeed;
        this.feedModelMapper = feedModelMapper;
    }

    @Override
    public void onResume() {
        getFeed(feedView.getFilterTag());
    }

    @Override
    public void onPause() {
        this.feedView.hideLoading();
    }

    @Override
    public void onDestroy() {
        this.getFeed.unsubscribe();
        this.filterFeed.unsubscribe();
        this.feedView = null;
    }

    public void setFeedView(FeedView feedView) {
        this.feedView = feedView;
    }

    public void removeFeedView() {
        this.feedView = null;
    }

    public void getFeed(String category) {
        this.getFeed.setCategory(category);
        this.getFeed.execute(new FeedSubscriber());
        if (this.feedView != null) {
            this.feedView.showLoading();
        }
    }

    public void filterFeed(String tag) {
        this.filterFeed.setCategory(tag);
        this.filterFeed.execute(new FeedSubscriber());
        if (this.feedView != null) {
            this.feedView.showLoading();
        }
    }

    private void showFeed(FeedModel feed) {
        if (this.feedView != null) {
            this.feedView.showFeed(feed);
        }
    }

    public void openFeed(final FeedItemModel feedModel) {
        if (this.feedView != null) {
            // Todo(tb) 31/07/16 use navigator class for navigating to the browser
            // Todo(tb) 31/07/16 validate url is valid http or https url
            Context context = this.feedView.getContext();
            Intent openInBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedModel.getUrl()));
            if (openInBrowserIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(openInBrowserIntent);
            }
        }
    }

    class FeedSubscriber extends Subscriber<Feed> {
        @Override
        public void onCompleted() {
            Log.d(FeedSubscriber.class.getSimpleName(), "completed");
        }

        @Override
        public void onError(Throwable e) {
            if (FeedPresenter.this.feedView != null) {
                if (e instanceof HttpException) {
                    // not 2xx http code
                    FeedPresenter.this.feedView.showInternetConnectionError();
                } else if (e instanceof IOException) {
                    // network or conversion error
                    FeedPresenter.this.feedView.showInternetConnectionError();
                } else if (e instanceof TimeoutException) {
                    // request timeout error
                    FeedPresenter.this.feedView.showRequestTimeoutError();
                } else {
                    FeedPresenter.this.feedView.showError();
                }
                FeedPresenter.this.feedView.hideLoading();
            } else {
                Log.d(FeedSubscriber.class.getSimpleName(), "onError without FeedView");
            }
        }

        @Override
        public void onNext(Feed feed) {
            if (FeedPresenter.this.feedView != null) {
                if (feed.getFeedItems().isEmpty()) {
                    FeedPresenter.this.feedView.showNoMatchingCategory();
                } else {
                    FeedPresenter.this.showFeed(feedModelMapper.transform(feed));
                }
                FeedPresenter.this.feedView.hideLoading();
            } else {
                Log.d(FeedSubscriber.class.getSimpleName(), "onNext without FeedView");
            }
        }
    }
}
