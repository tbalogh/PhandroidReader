package tbalogh.rssreader.data.entity;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import io.realm.RealmObject;

/**
 * Created by tbalogh on 29/07/16.
 */
@Root(name = "category")
public class CategoryEntity extends RealmObject {

    @Text
    String category;

    public CategoryEntity() {}

    public String getCategory() {
        return category;
    }
}
