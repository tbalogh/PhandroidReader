package tbalogh.rssreader.presentation.presenter;

import android.test.AndroidTestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tbalogh.rssreader.domain.interactor.FilterFeedInteractor;
import tbalogh.rssreader.domain.interactor.GetFeedInteractor;
import tbalogh.rssreader.presentation.mapper.FeedModelMapper;
import tbalogh.rssreader.presentation.view.FeedView;
import tbalogh.rssreader.presentation.view.presenter.FeedPresenter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by tbalogh on 31/07/16.
 */
public class FeedPresenterTest extends AndroidTestCase {

    @Mock private FeedView             mockFeedView;
    @Mock private GetFeedInteractor    mockGetFeedInteractor;
    @Mock private FilterFeedInteractor mockFilterFeedInteractor;
    @Mock private FeedModelMapper      mockFeedModelMapper;

    private FeedPresenter feedPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        feedPresenter = new FeedPresenter(mockGetFeedInteractor, mockFilterFeedInteractor,
                mockFeedModelMapper);
        feedPresenter.setFeedView(mockFeedView);
    }

    public void testOnResume() {
        given(mockFeedView.getFilterTag()).willReturn("");
        feedPresenter.onResume();

        verify(mockFeedView).showLoading();
        verify(mockFeedView).hideLoading();
    }
}
