package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class OhhhNoTest {

    private OhhhNo achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new OhhhNo();
        changeset = mock(Changeset.class);
    }

    @Test
    public void testNoOhhhNo() {
        String message = "There should be nothing in this sentence";

        when(changeset.getMessage()).thenReturn(message);
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGESET, achievement.getType());
    }

    @Test
    public void testWithOhhhNo() {
        String message = "Ohhh No!";

        when(changeset.getMessage()).thenReturn(message);
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testWordBoundries() {
        String message = "There should be noohhhno in this sentence";

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
