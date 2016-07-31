package tbalogh.rssreader.data.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by tbalogh on 29/07/16.
 */

@Root(name = "channel", strict = false)
public class FeedEntity extends RealmObject {

    @Path("title")
    @Text
    String title;

    @ElementList(inline = true, name = "item")
    RealmList<FeedItemEntity> feedItemEntities;

    public FeedEntity() {
        feedItemEntities = new RealmList<>();
    }

    public RealmList<FeedItemEntity> getFeedItemEntities() {
        return feedItemEntities;
    }
}
