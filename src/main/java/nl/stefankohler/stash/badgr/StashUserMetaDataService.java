package nl.stefankohler.stash.badgr;

import static com.google.common.base.Preconditions.*;

import javax.validation.constraints.NotNull;

import net.java.ao.DBParam;
import net.java.ao.Query;
import nl.stefankohler.stash.badgr.model.StashUserMetaData;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.stash.user.StashUser;

/**
 * Default Service for storing and retrieving StashUserMetaData from
 * the database.
 * 
 * @author Stefan Kohler
 * @since 1.1
 */
public class StashUserMetaDataService {

    private final ActiveObjects ao;

    /**
     * Constructs the {@link StashUserMetaDataService}.
     * @param ao the {@link ActiveObjects} for accessing the database.
     */
    public StashUserMetaDataService(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    public StashUserMetaData store(@NotNull StashUser stashUser, @NotNull String metaKey, @NotNull String metaValue) {
        StashUserMetaData metadata = find(stashUser, metaKey);
        if (metadata == null) {
            metadata = ao.create(StashUserMetaData.class, //
                    new DBParam("USER_ID", stashUser.getId()), //
                    new DBParam("META_KEY", metaKey), //
                    new DBParam("META_VALUE", metaValue));
        } else {
            metadata.setMetaValue(metaValue);
            metadata.save();
        }
        return metadata;
    }

    public StashUserMetaData find(StashUser stashUser, String metaKey) {
        Query query = Query.select().where("USER_ID = ? and META_KEY = ?", stashUser.getId(), metaKey);
        StashUserMetaData[] result = ao.find(StashUserMetaData.class, query);
        if (result.length > 0) {
            return result[0];
        }
        return null;
    }
}
