package tbalogh.rssreader.presentation.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tbalogh.rssreader.data.repository.DbDataSource;
import tbalogh.rssreader.data.repository.FeedRepositoryImpl;
import tbalogh.rssreader.data.repository.RealmDataSource;
import tbalogh.rssreader.domain.repository.FeedRepository;
import tbalogh.rssreader.presentation.RssReaderApplication;

/**
 * Created by tbalogh on 28/07/16.
 */
@Module
public class ApplicationModule {

    private final RssReaderApplication application;

    public ApplicationModule(RssReaderApplication application) {
        this.application = application;
    }

    @Provides
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public FeedRepository provideFeedRepository(FeedRepositoryImpl feedRepository) {
        return feedRepository;
    }

    @Provides
    @Singleton
    public DbDataSource provideDbDataSource(RealmDataSource realmDataSource) {
        return realmDataSource;
    }
}
