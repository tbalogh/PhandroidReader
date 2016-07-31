package tbalogh.rssreader.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmList;
import tbalogh.rssreader.data.entity.CategoryEntity;
import tbalogh.rssreader.data.entity.FeedEntity;
import tbalogh.rssreader.data.entity.FeedItemEntity;
import tbalogh.rssreader.data.entity.RssEntity;
import tbalogh.rssreader.domain.Feed;
import tbalogh.rssreader.domain.FeedItem;

/**
 * Created by tbalogh on 29/07/16.
 */
@Singleton
public class RssEntityMapper {

    @Inject
    public RssEntityMapper() {}

    public Feed transform(RssEntity rssEntity) {
        if (rssEntity == null) {
            return Feed.emptyFeed();
        }
        Feed feed = transform(rssEntity.getFeedEntity());
        return feed == null ? Feed.emptyFeed() : feed;
    }

    private Feed transform(FeedEntity feedEntity) throws IllegalArgumentException {
        if (feedEntity == null) {
            return null;
        }
        return new Feed(transform(feedEntity.getFeedItemEntities()));
    }

    private List<FeedItem> transform(RealmList<FeedItemEntity> feedItemEntities) {
        List<FeedItem> feedItems = new ArrayList<>();
        for (FeedItemEntity feedItemEntity : feedItemEntities) {
            feedItems.add(transform(feedItemEntity));
        }
        return feedItems;
    }

    private FeedItem transform(FeedItemEntity feedItemEntity) {
        return new FeedItem(
                feedItemEntity.getTitle(),
                feedItemEntity.getDescription(),
                feedItemEntity.getUrl(),
                feedItemEntity.getImageUrl(),
                feedItemEntity.getDateTime(),
                transformCategories(feedItemEntity.getCategoryEntities()),
                false);
    }

    private List<String> transformCategories(RealmList<CategoryEntity> categoryEntities) {
        List<String> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            categories.add(categoryEntity.getCategory());
        }
        return categories;
    }
}
