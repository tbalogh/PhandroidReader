package tbalogh.rssreader.domain.interactor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import tbalogh.rssreader.domain.repository.FeedRepository;

/**
 * Created by tbalogh on 29/07/16.
 */
public class FilterFeedInteractor extends Interactor {

    private final FeedRepository feedRepository;
    private       String         category;

    @Inject
    public FilterFeedInteractor(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    protected Observable buildInteraction() {
        return this.feedRepository.filterFeed(category)
                                  .delay(200, TimeUnit.MILLISECONDS);
    }
}
