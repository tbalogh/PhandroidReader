package tbalogh.rssreader.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import tbalogh.rssreader.data.entity.RssEntity;
import tbalogh.rssreader.data.entity.mapper.RssEntityMapper;
import tbalogh.rssreader.data.net.PhandroidAPI;
import tbalogh.rssreader.domain.Feed;
import tbalogh.rssreader.domain.FeedItem;
import tbalogh.rssreader.domain.repository.FeedRepository;

/**
 * Created by tbalogh on 27/07/16.
 */
@Singleton
public class FeedRepositoryImpl implements FeedRepository {

    private static final String NO_FEED_FOUND_ERROR_MESSAGE = "No feed found in the database";

    private final PhandroidAPI    phandroidApi;
    private final RssEntityMapper rssEntityMapper;
    private final DbDataSource    dbDataSource;

    @Inject
    public FeedRepositoryImpl(Retrofit retrofit, RssEntityMapper rssEntityMapper,
                              DbDataSource dbDataSource) {
        this.phandroidApi = retrofit.create(PhandroidAPI.class);
        this.rssEntityMapper = rssEntityMapper;
        this.dbDataSource = dbDataSource;
    }

    @Override
    public Observable<Feed> feed(String category) {
        return this.phandroidApi.feed()
                                .subscribeOn(Schedulers.io())
                                .doOnNext(dbDataSource::saveRssEntity)
                                .map(rssEntityMapper::transform)
                                .map(feed -> filterByCategory(feed, category))
                                .flatMap(Observable::just);
    }


    @Override
    public Observable<Feed> filterFeed(String category) {
        return Observable.create(new Observable.OnSubscribe<Feed>() {
            @Override
            public void call(Subscriber<? super Feed> subscriber) {
                RssEntity rssEntity = dbDataSource.getRssEntity();
                if (rssEntity == null) {
                    subscriber.onError(new Exception(NO_FEED_FOUND_ERROR_MESSAGE));
                } else {
                    subscriber.onNext(
                            filterByCategory(rssEntityMapper.transform(rssEntity), category));
                    subscriber.onCompleted();
                }
            }
        });
    }

    private Feed filterByCategory(Feed feed, String category) {
        if (category == null || category.isEmpty()) {
            return feed;
        }

        List<FeedItem> filteredFeedItems = new ArrayList<>();
        for (int i = 0; i < feed.getFeedItems().size(); i++) {
            FeedItem feedItem = feed.getFeedItems().get(i);
            if (feedItem.matchCategory(category)) {
                filteredFeedItems.add(feedItem);
            }
        }
        feed.setFeedItems(filteredFeedItems);
        return feed;
    }
}
