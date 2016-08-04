package tbalogh.rssreader.presentation.presenter;

import android.test.AndroidTestCase;

import rx.Subscriber;
import tbalogh.rssreader.domain.interactor.FilterFeedInteractor;
import tbalogh.rssreader.domain.interactor.GetFeedInteractor;
import tbalogh.rssreader.presentation.mapper.FeedModelMapper;
import tbalogh.rssreader.presentation.view.FeedView;
import tbalogh.rssreader.presentation.view.presenter.FeedPresenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by tbalogh on 31/07/16.
 */
public class FeedPresenterTest extends AndroidTestCase {

    private FeedView             mockFeedView;
    private GetFeedInteractor    mockGetFeedInteractor;
    private FilterFeedInteractor mockFilterFeedInteractor;
    private FeedModelMapper      mockFeedModelMapper;

    private FeedPresenter feedPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        initMocks();
        feedPresenter = new FeedPresenter(mockGetFeedInteractor, mockFilterFeedInteractor,
                mockFeedModelMapper);
        feedPresenter.setFeedView(mockFeedView);
    }

    public void testGetFeed() {
        feedPresenter.getFeed(anyString());

        verify(mockFeedView).showLoading();
        verify(mockGetFeedInteractor).setCategory(anyString());
        verify(mockGetFeedInteractor).execute(any(Subscriber.class));
    }


    private void initMocks() {
        mockFeedView = mock(FeedView.class);
        mockGetFeedInteractor = mock(GetFeedInteractor.class);
        mockFilterFeedInteractor = mock(FilterFeedInteractor.class);
        mockFeedModelMapper = mock(FeedModelMapper.class);
    }
}
