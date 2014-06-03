package nl.stefankohler.stash.badgr.model;

import net.java.ao.Entity;

public interface StashUserMetaData extends Entity {

    String LAST_PUSH_KEY = "badgr.last.push";
    String LAST_PULL_KEY = "badgr.last.pull";

    Integer getUserId();

    void setUserId(Integer userId);

    String getMetaKey();

    void setMetaKey(String metaKey);

    String getMetaValue();

    void setMetaValue(String value);

}
