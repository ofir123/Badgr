package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class BadgrBadgrBadgrTest {

    private BadgrBadgrBadgr achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new BadgrBadgrBadgr();
        changeset = mock(Changeset.class);
    }

    @Test
    public void testNoBadgr() {
        String message = "There should be no Badger in this sentence";

        when(changeset.getMessage()).thenReturn(message);
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGESET, achievement.getType());
    }

    @Test
    public void testWithBadgr() {
        String message = "There should be badgr in this sentence";

        when(changeset.getMessage()).thenReturn(message);
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testWordBoundries() {
        String message = "There should be nobadgr in this sentence";

        when(changeset.getMessage()).thenReturn(message);
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testWithEmptyCommit() {
        String message = null;

        when(changeset.getMessage()).thenReturn(message);
        assertFalse(achievement.isConditionMet(changeset));
    }

}
