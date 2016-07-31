package tbalogh.rssreader.data.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by tbalogh on 29/07/16.
 */
@Root(name = "item", strict = false)
public class FeedItemEntity extends RealmObject {

    @Element(name = "title")
    String title;

    @Element(name = "link")
    String url;

    @Element(name = "description")
    String description;

    @Element(name = "image", required = false)
    String imageUrl;

	@Element(name = "pubDate", required = false)
	String dateTime;

    @ElementList(name = "category", inline = true)
    RealmList<CategoryEntity> categoryEntities;

    public FeedItemEntity() {}

	public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

	public String getDateTime() {
		return dateTime;
	}

	public RealmList<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }
}
