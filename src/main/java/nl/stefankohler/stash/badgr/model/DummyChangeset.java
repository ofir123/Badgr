package nl.stefankohler.stash.badgr.model;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.atlassian.stash.content.AttributeMap;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.content.MinimalChangeset;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.Person;

public class DummyChangeset implements Changeset {

    private final String displayId;
    private final String id;

    public DummyChangeset(String id) {
        this(id, null);
    }

    public DummyChangeset(String id, String displayId) {
        this.id = id;
        if (displayId != null) {
            this.displayId = displayId;
        } else {
            this.displayId = id.substring(0, 7);
        }
    }

    @Override
    public String getDisplayId() {
        return displayId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Set<String> getAttributeValues(String arg0) {
        return null;
    }

    @Override
    public AttributeMap getAttributes() {
        return null;
    }

    @Override
    public Person getAuthor() {
        return null;
    }

    @Override
    public Date getAuthorTimestamp() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Collection<MinimalChangeset> getParents() {
        return null;
    }

    @Override
    public Repository getRepository() {
        return null;
    }

}
