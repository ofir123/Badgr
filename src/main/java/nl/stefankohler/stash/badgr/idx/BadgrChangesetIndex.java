package nl.stefankohler.stash.badgr.idx;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import nl.stefankohler.stash.badgr.AchievementManager;
import nl.stefankohler.stash.badgr.achievements.Achievement;
import nl.stefankohler.stash.badgr.model.AoCount;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.stash.commit.CommitService;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.content.ChangesetsBetweenRequest;
import com.atlassian.stash.content.MinimalChangeset;
import com.atlassian.stash.idx.ChangesetIndexer;
import com.atlassian.stash.idx.IndexingContext;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.Person;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;
import com.atlassian.stash.util.PageRequestImpl;
import com.google.common.collect.Maps;

public class BadgrChangesetIndex implements ChangesetIndexer {

    private static final Logger LOG = LoggerFactory.getLogger(BadgrChangesetIndex.class);

    private static final String BADGER_INDEXING_STATE = "nl.stefankohler.stash.BadgrIndexingState";
    private static final String ENV_KEY_ENABLE_INDEXING = "stash.badgr.enable.indexing";
    private static final String ID = "nl.stefankohler.stash.BadgrChangesetIndex";
    private static final int PAGE_LIMIT = 250;

    private final CommitService commitService;
    private final AchievementManager achievementManager;
    private final TransactionTemplate transactionTemplate;

    public BadgrChangesetIndex(TransactionTemplate transactionTemplate, CommitService CommitService,
                               AchievementManager achievementManager) {

        this.commitService = checkNotNull(CommitService);
        this.achievementManager = checkNotNull(achievementManager);
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public String getId() {
        return ID;
    }

    /**
     * @param repository the repository.
     * @return whether the indexer should index changesets for {@code repository}
     */
    @Override
    public boolean isEnabledForRepository(Repository repository) {
        String enabled = System.getProperty(ENV_KEY_ENABLE_INDEXING);
        if (enabled != null) {
            return "true".equals(enabled.toLowerCase(Locale.getDefault()));
        }
        return true;
    }

    @Override
    public void onBeforeIndexing(IndexingContext context) {
        context.put(BADGER_INDEXING_STATE, new AchievementContext());
    }

    @Override
    public void onAfterIndexing(IndexingContext context) {
        AchievementContext achievementContext = context.get(BADGER_INDEXING_STATE);
        if (achievementContext != null) {
            achievementContext.updateAchievements();
        }
    }

    @Override
    public void onChangesetRemoved(Changeset changeset, IndexingContext ctx) {
        // TODO should we remove that stats?
    }

    @Override
    public void onChangesetAdded(Changeset changeset, IndexingContext ctx) {
        if (StringUtils.isBlank(changeset.getAuthor().getEmailAddress())) return;

        AchievementContext achievementContext = ctx.get(BADGER_INDEXING_STATE);
        
        Collection<MinimalChangeset> parents = changeset.getParents();
        String parentId = (parents.isEmpty()) ? "" : parents.iterator().next().getId();

        processObject(achievementContext, changeset, changeset, Achievement.AchievementType.CHANGESET);

        processChanges(achievementContext, changeset, parentId, new PageRequestImpl(0, PAGE_LIMIT));
    }

    private void processChanges(AchievementContext achievementContext, Changeset changeset, String parentId, PageRequest pageRequest) {
      ChangesetsBetweenRequest request = new ChangesetsBetweenRequest.Builder(changeset.getRepository())
      .exclude(changeset.getId())
      .include(parentId)
      .build();  
      
      
        Page<Changeset> changes = commitService.getChangesetsBetween(request, pageRequest);
        for (Changeset change : changes.getValues()) {
            processObject(achievementContext, changeset, change, Achievement.AchievementType.CHANGE);
        }

        if (!changes.getIsLastPage()) {
            PageRequest nextPageRequest = changes.getNextPageRequest();

            // release the memory before recursing
            changes = null;
            processChanges(achievementContext, changeset, parentId, nextPageRequest);
        }
    }

    private void processObject(AchievementContext achievementContext, Changeset changeset,
                               Object validator, Achievement.AchievementType achievementType) {
        try {
            for (Achievement achievement : achievementManager.getAchievements(achievementType)) {
                if (achievement.isConditionMet(validator)) {
                    achievementContext.incrementCount(achievement, changeset.getAuthor(), changeset);
                }
            }
        } catch (Exception e) {
            // catch-all; if there is an exception, log the message but continue the indexing.
            LOG.error("Couldn't process object: " + validator, e);
        }
    }

    private class AchievementContext {

        private final Map<String, Map<Achievement, AchievementDelta>> achievementsForPerson = Maps.newHashMap();
        
        public void incrementCount(Achievement achievement, Person person, Changeset changeset) {
            Map<Achievement, AchievementDelta> deltasForAchievement = achievementsForPerson.get(person.getEmailAddress());
            if (deltasForAchievement == null) {
                deltasForAchievement = Maps.newHashMap();
                achievementsForPerson.put(person.getEmailAddress(), deltasForAchievement);
            }
            AchievementDelta delta = deltasForAchievement.get(achievement);
            if (delta == null) {
                delta = new AchievementDelta();
                AoCount count = achievementManager.getCounting(achievement, person);
                delta.startCount = count != null ? count.getAmount() : 0;
                deltasForAchievement.put(achievement, delta);
            }
            delta.indexCount++;
            if ((delta.indexCount + delta.startCount) == achievement.getCountingLimit()) {
                delta.grantedOnChangeset = changeset;
            }
        }
        
        public void updateAchievements() {
            for (Map.Entry<String, Map<Achievement, AchievementDelta>> personEntry : achievementsForPerson.entrySet()) {
                final Person person = new SimplePerson(personEntry.getKey());
                final Map<Achievement, AchievementDelta> achievementDeltaMap = personEntry.getValue();
                transactionTemplate.execute(new TransactionCallback<Void>() {
                    @Override
                    public Void doInTransaction() {
                        for (Map.Entry<Achievement, AchievementDelta> deltaEntry : achievementDeltaMap.entrySet()) {
                            Achievement achievement = deltaEntry.getKey();
                            AchievementDelta delta = deltaEntry.getValue();
                            if (delta.indexCount != 0) {
                                achievementManager.addCounting(achievement, person, delta.indexCount);
                                if (delta.grantedOnChangeset != null) {
                                    achievementManager.grantAchievement(achievement, person, delta.grantedOnChangeset);
                                }
                            }
                        }
                        return null;
                    }
                });
            }
        }
    }
   
    private static class SimplePerson implements Person {
        private final String email;

        private SimplePerson(String email) {
            this.email = email;
        }

        @Override
        public String getEmailAddress() {
            return email;
        }

        @Override
        public String getName() {
            return "not relevant";
        }
    }
    
    private static class AchievementDelta {
        private int startCount;
        private int indexCount;
        private Changeset grantedOnChangeset;
    }
    
}