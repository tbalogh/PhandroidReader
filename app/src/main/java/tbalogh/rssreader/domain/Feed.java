package tbalogh.rssreader.domain;


import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;


/**
 * Created by tbalogh on 29/07/16.
 */
public class Feed {
    private final List<FeedItem> feedItems;

    public Feed(List<FeedItem> feedItems) {
        this.feedItems = feedItems == null ? Collections.emptyList() : feedItems;
    }

    @NonNull
    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems.clear();
        this.feedItems.addAll(feedItems);
    }

    public static Feed emptyFeed() {
        return new Feed(Collections.emptyList());
    }
}
