package tbalogh.rssreader.domain.repository;

import rx.Observable;
import tbalogh.rssreader.domain.Feed;

/**
 * Created by tbalogh on 27/07/16.
 */
public interface FeedRepository {
    Observable<Feed> feed(String category);

    Observable<Feed> filterFeed(String category);
}
