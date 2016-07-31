package tbalogh.rssreader.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import tbalogh.rssreader.domain.Feed;
import tbalogh.rssreader.domain.FeedItem;
import tbalogh.rssreader.presentation.di.PerActivity;
import tbalogh.rssreader.presentation.model.FeedItemModel;
import tbalogh.rssreader.presentation.model.FeedModel;

/**
 * Created by tbalogh on 29/07/16.
 */
@PerActivity
public class FeedModelMapper {

    @Inject
    FeedModelMapper() {}

    public FeedModel transform(Feed feed) {
        if (feed == null) {
            return FeedModel.emptyFeedModel();
        }
        List<FeedItemModel> feedItemModels = transform(feed.getFeedItems());
        return feedItemModels == null ? FeedModel.emptyFeedModel() : new FeedModel(feedItemModels);
    }

    private List<FeedItemModel> transform(List<FeedItem> feedItems) {
        if (feedItems == null) {
            return null;
        }
        List<FeedItemModel> feedItemModels = new ArrayList<>();
        for (FeedItem feedItem : feedItems) {
            feedItemModels.add(transform(feedItem));
        }
        return feedItemModels;
    }

    private FeedItemModel transform(FeedItem feedItem) {
        return new FeedItemModel(
                feedItem.getTitle(),
                feedItem.getDescription(),
                feedItem.getUrl(),
                feedItem.getPhotoUrl(),
                feedItem.getDateTime(),
                feedItem.getCategories(),
                feedItem.isSeen());
    }
}
