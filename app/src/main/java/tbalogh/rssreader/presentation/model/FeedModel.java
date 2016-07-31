package tbalogh.rssreader.presentation.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by tbalogh on 27/07/16.
 */
public class FeedModel {
    private final List<FeedItemModel> feedItems;

    public FeedModel(List<FeedItemModel> feedItems) {
        this.feedItems = feedItems == null ? Collections.emptyList() : feedItems;
    }

    public List<FeedItemModel> getFeedItems() {
        return feedItems;
    }

    public static FeedModel emptyFeedModel() {
        return new FeedModel(Collections.emptyList());
    }
}
