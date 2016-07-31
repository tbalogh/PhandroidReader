package tbalogh.rssreader.data.net;

import retrofit2.http.GET;
import rx.Observable;
import tbalogh.rssreader.data.entity.RssEntity;

/**
 * Created by tbalogh on 28/07/16.
 */
public interface PhandroidAPI {
    @GET("/AndroidPhoneFans")
    Observable<RssEntity> feed();
}
