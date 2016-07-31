package tbalogh.rssreader.presentation.view;

import android.content.Context;

import tbalogh.rssreader.presentation.model.FeedModel;

/**
 * Created by tbalogh on 27/07/16.
 */
public interface FeedView extends LoadingView {
    void showFeed(FeedModel feed);

	void showNoMatchingCategory();

	void showInternetConnectionError();

	void showRequestTimeoutError();

	void showError();

    String getFilterTag();

	Context getContext();
}
