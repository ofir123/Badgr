package nl.stefankohler.stash.badgr.achievements;

import static com.google.common.base.Preconditions.*;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.content.MinimalChangeset;
import com.atlassian.stash.history.HistoryService;

/**
 * The Raising the Dead Achievement.
 * Granted if a commit is made to an repository where the
 * last commit has been over X months ago.
 * 
 * @author Stefan Kohler
 * @since 1.0
 * 
 * @deprecated Decided to abandon the Raising the Dead Achievement based
 *             up performance issues in getting the parent changesets (about 100ms per call)
 */
public class RaisingTheDead extends AbstractAchievement {

    private static final int MONTHS_AGO = 6;

    private final HistoryService historyService;

    /**
     * Constructs a new RaisingTheDead achievement.
     * @param historyService HistoryService implementation to check the last commit to a repo.
     */
    @Autowired
    public RaisingTheDead(HistoryService historyService) {
        super();
        this.historyService = checkNotNull(historyService);
    }

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "raisingthedead.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        if (!changeset.getParents().isEmpty()) {
            MinimalChangeset parent = changeset.getParents().iterator().next();
            Changeset previous = historyService.getChangeset(changeset.getRepository(), parent.getId());

            DateTime createdOn = new DateTime(changeset.getAuthorTimestamp());
            DateTime previousCreated = new DateTime(previous.getAuthorTimestamp());

            return (createdOn.minusMonths(MONTHS_AGO).isAfter(previousCreated)) ? true : false;
        }
        return false;

    }
}
