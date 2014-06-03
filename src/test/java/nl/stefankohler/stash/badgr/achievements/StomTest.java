package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class StomTest {

    private Stom achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new Stom();
        changeset = mock(Changeset.class);
    }

    @Test
    public void testNoStom() {
        String message = "There should be nothing in this sentence";

        when(changeset.getMessage()).thenReturn(message);
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGESET, achievement.getType());
    }

    @Test
    public void testWithStom() {
        String message = "Just Stom!";

        when(changeset.getMessage()).thenReturn(message);
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testWordBoundries() {
        String message = "There should be nostom in this sentence";

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
