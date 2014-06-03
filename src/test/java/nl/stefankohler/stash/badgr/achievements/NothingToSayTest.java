package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class NothingToSayTest {

    private NothingToSay achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new NothingToSay();
        changeset = mock(Changeset.class);
    }

    @Test
    public void testEmptyMessage() {
        when(changeset.getMessage()).thenReturn("");
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testNullMessage() {
        when(changeset.getMessage()).thenReturn(null);
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testMessage() {
        when(changeset.getMessage()).thenReturn("change!");
        assertFalse(achievement.isConditionMet(changeset));
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
        assertTrue(Integer.valueOf(10).equals(achievement.getCountingLimit()));
    }

}
