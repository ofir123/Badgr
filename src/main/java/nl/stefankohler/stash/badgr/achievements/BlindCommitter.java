package nl.stefankohler.stash.badgr.achievements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nl.stefankohler.stash.badgr.Achievement;
import nl.stefankohler.stash.badgr.StashUserMetaDataService;
import nl.stefankohler.stash.badgr.model.StashUserMetaData;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.stash.event.RepositoryEvent;
import com.atlassian.stash.user.StashUser;

@Achievement
public class BlindCommitter extends AbstractAchievement {

    private static final int DAYS_AGO = 3;

    private final StashUserMetaDataService metaDataService;

    @Autowired
    public BlindCommitter(StashUserMetaDataService metaDataService) {
        super();
        this.metaDataService = metaDataService;
    }

    @Override
    public AchievementType getType() {
        return AchievementType.EVENT;
    }

    @Override
    public String getBadge() {
        return "blindcommitter.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        RepositoryEvent event = (RepositoryEvent) subject;

        DateTime lastPullDate = getLastPullDate(event.getUser());
        if (lastPullDate != null) {
            DateTime pushDate = new DateTime(event.getDate());
            return pushDate.minusDays(DAYS_AGO).isAfter(lastPullDate);
        }
        return false;
    }

    private DateTime getLastPullDate(StashUser stashUser) {
        try {
            StashUserMetaData metaData = metaDataService.find(stashUser, StashUserMetaData.LAST_PULL_KEY);
            if (metaData != null) {
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                Date date = format.parse(metaData.getMetaValue());
                return new DateTime(date);
            }
        } catch (ParseException exception) {
            // nothing?
        }
        return null;
    }
}
