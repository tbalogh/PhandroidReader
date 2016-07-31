package tbalogh.rssreader.domain.interactor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import tbalogh.rssreader.domain.repository.FeedRepository;

/**
 * Created by tbalogh on 27/07/16.
 */

public class GetFeedInteractor extends Interactor {

    public static final int TIMEOUT = 1000;

    private final FeedRepository repository;
    private       String         category;

    @Inject
    public GetFeedInteractor(FeedRepository repository) {
        this.repository = repository;
        category = "";
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    protected Observable buildInteraction() {
        return this.repository.feed(category).timeout(TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
