package nl.stefankohler.stash.badgr.achievements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.stefankohler.stash.badgr.Achievement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.stash.content.Changeset;

/**
 * The BugHunter Achievement.
 * Granted after X number of commits with an JIRA ticket
 * reference in the message has been commited to Stash.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class BugHunter extends AbstractAchievement {
    private static final Logger LOG = LoggerFactory.getLogger(BugHunter.class);

    private static final String ENV_KEY_PATTERN_STRING = "stash.jira.key.pattern";
    private static final Pattern DEFAULT_PATTERN = Pattern.compile("\\b([a-zA-Z\\-]+-\\d+)");

    private static final Integer COUNTING_LIMIT = Integer.valueOf(50);

    private final Pattern pattern;

    /**
     * Constructs a new BugHunter Achievement.
     */
    public BugHunter() {
        super();
        Pattern p = DEFAULT_PATTERN;
        String patternString = System.getProperty(ENV_KEY_PATTERN_STRING);
        if (patternString != null) {
            try {
                p = Pattern.compile(patternString);
            } catch (IllegalArgumentException e) {
                LOG.error("Couldn't compile custom JIRA issue key pattern", e);
            }
        }
        pattern = p;
    }

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "bughunter.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        // index any substring that matches
        Matcher matcher = pattern.matcher(changeset.getMessage());
        while (matcher.find()) {
            final String jiraKey = matcher.group(1);
            int index = jiraKey.indexOf('-', jiraKey.indexOf('-') + 1);
            return (index == -1);
        }

        return false;
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}
