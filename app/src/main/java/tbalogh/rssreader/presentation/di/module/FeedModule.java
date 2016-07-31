package tbalogh.rssreader.presentation.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import tbalogh.rssreader.domain.interactor.FilterFeedInteractor;
import tbalogh.rssreader.domain.interactor.GetFeedInteractor;
import tbalogh.rssreader.domain.interactor.Interactor;
import tbalogh.rssreader.domain.repository.FeedRepository;
import tbalogh.rssreader.presentation.di.PerActivity;

/**
 * Created by tbalogh on 27/07/16.
 */
@Module
public class FeedModule {
    @Provides
    @PerActivity
    @Named("getFeed")
    public Interactor provideGetFeedInteractor(FeedRepository feedRepository) {
        return new GetFeedInteractor(feedRepository);
    }

    @Provides
    @PerActivity
    @Named("filterFeed")
    public Interactor provideFilterFeedInteractior(FeedRepository feedRepository) {
        return new FilterFeedInteractor(feedRepository);
    }
}
