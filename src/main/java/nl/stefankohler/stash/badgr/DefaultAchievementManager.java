package nl.stefankohler.stash.badgr;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.java.ao.DBParam;
import net.java.ao.Query;
import nl.stefankohler.stash.badgr.achievements.Achievement;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;
import nl.stefankohler.stash.badgr.event.AchievementGrantedEvent;
import nl.stefankohler.stash.badgr.model.AoAchievement;
import nl.stefankohler.stash.badgr.model.AoCount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.user.Person;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Default Implementation for the {@link AchievementManager}.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
public class DefaultAchievementManager implements AchievementManager {
    private final static Logger LOG = LoggerFactory.getLogger(DefaultAchievementManager.class);

    private final ActiveObjects ao;
    private final EventPublisher eventPublisher;

    private List<Achievement> achievements = new ArrayList<Achievement>();

    /**
     * Constructs the {@link DefaultAchievementManager}.
     * @param ao the {@link ActiveObjects} for storing and receiving achievements.
     * @param eventPublisher The {@link EventPublisher} for triggering events
     */
    public DefaultAchievementManager(ActiveObjects ao, EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        this.ao = checkNotNull(ao);
    }

    @Override
    public void register(Achievement achievement) {
        boolean devMode = Boolean.parseBoolean(System.getProperty("atlassian.dev.mode", "false"));
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);

            if (devMode) LOG.info("Registered Achievement: " + achievement.getCode());
        }
    }

    @Override
    public Achievement getAchievement(String code) {
        for (Achievement achievement : achievements) {
            if (achievement.getCode().equals(code)) return achievement;
        }
        return null;
    }

    @Override
    public Achievement getAchievement(Class<? extends Achievement> clazz) {
        for (Achievement achievement : achievements) {
            if (achievement.getClass().equals(clazz)) return achievement;
        }
        return null;
    }

    @Override
    public List<Achievement> getAchievements() {
        return ImmutableList.copyOf(achievements);
    }

    @Override
    public List<Achievement> getAchievements(final Achievement.AchievementType type) {
        return ImmutableList.copyOf(Iterables.filter(achievements, new TypePredicate(type)));
    }

    @Override
    public List<Achievement> getAchievementsForEmail(String email) {
        final List<AoAchievement> granted = Arrays.asList(ao.find(AoAchievement.class, Query.select("CODE").where("EMAIL = ?", email)));
        return ImmutableList.copyOf(Iterables.filter(achievements, new AchievedPredicate(granted)));
    }

    @Override
    public void grantAchievement(Achievement achievement, Person person, Changeset changeset) {
        checkNotNull(achievement, "Achievement must not be null");
        checkNotNull(person, "Person must not be null");
        checkNotNull(person.getEmailAddress(), "User email must not be null");
        checkNotNull(changeset, "Changeset must not be null");

        int count = ao.count(AoAchievement.class, Query.select().where("CODE = ? AND EMAIL = ?", achievement.getCode(), person.getEmailAddress()));

        if (count < 1) {
            ao.create(AoAchievement.class, //
                    new DBParam("CODE", achievement.getCode()), //
                    new DBParam("EMAIL", person.getEmailAddress()), //
                    new DBParam("CHANGESET_ID", changeset.getId()), //
                    new DBParam("CREATED", changeset.getAuthorTimestamp()), //
                    new DBParam("REPOSITORY", changeset.getRepository().getId()));
            eventPublisher.publish(new AchievementGrantedEvent(this, achievement, person));
        }
    }

    @Override
    public Integer addCounting(Achievement achievement, Person person, Integer add) {
        checkNotNull(achievement, "Achievement must not be null");
        checkNotNull(person, "Person must not be null");
        checkNotNull(person.getEmailAddress(), "User email must not be null");

        AoCount count = getCounting(achievement, person);
        if (count == null) {
            count = ao.create(AoCount.class, //
                    new DBParam("CODE", achievement.getCode()), //
                    new DBParam("EMAIL", person.getEmailAddress()), //
                    new DBParam("AMOUNT", 0));
        }
        count.setAmount(count.getAmount() + add);
        count.save();

        return count.getAmount();
    }

    @Override
    public AoCount getCounting(Achievement achievement, Person person) {
        AoCount[] result = ao.find(AoCount.class, Query.select().where("CODE = ? AND EMAIL = ?", achievement.getCode(), person.getEmailAddress()));
        if (result.length > 0) {
            return result[0];
        }
        return null;
    }

    /**
     * Filters the available Achievements with achievements which are already
     * granted to the user.
     */
    private static final class AchievedPredicate implements Predicate<Achievement> {
        private final List<AoAchievement> results;

        public AchievedPredicate(List<AoAchievement> results) {
            this.results = results;
        }

        @Override
        public boolean apply(Achievement achievement) {
            for (AoAchievement aoAchievement : results) {
                if (achievement.getCode().equals(aoAchievement.getCode())) return true;
            }
            return false;
        }
    }

    /**
     * Filters Achievements based on AchievementType.
     */
    private static final class TypePredicate implements Predicate<Achievement> {
        private final AchievementType achievementType;

        public TypePredicate(AchievementType achievementType) {
            this.achievementType = achievementType;
        }

        @Override
        public boolean apply(Achievement achievement) {
            return achievement.getType() == achievementType;
        }
    }
}
