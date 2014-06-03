package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.content.Changeset;

@RunWith(MockitoJUnitRunner.class)
public class BugHunterTest {

    private BugHunter achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new BugHunter();
        changeset = mock(Changeset.class);
    }

    @Test
    public void shouldHaveBadge() {
        assertNotNull(achievement.getBadge());
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGESET, achievement.getType());
    }

    @Test
    public void shouldHaveCountingLimit() {
        assertTrue(Integer.valueOf(50).equals(achievement.getCountingLimit()));
    }

    @Test
    public void testNoKey() {
        when(changeset.getMessage()).thenReturn("This message has no key");
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testJiraKeyStart() {
        when(changeset.getMessage()).thenReturn("CAV-111: moved");
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testJiraKeyMiddle() {
        when(changeset.getMessage()).thenReturn("Moved CAV-111 elsewhere");
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testJiraKeyEnd() {
        when(changeset.getMessage()).thenReturn("Moved to CAV-111");
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testWrongJiraKey() {
        when(changeset.getMessage()).thenReturn("Review in ST-CAV-111 done!");
        assertFalse(achievement.isConditionMet(changeset));
    }

}
