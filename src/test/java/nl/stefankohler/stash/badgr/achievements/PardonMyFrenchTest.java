package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class PardonMyFrenchTest {

    private PardonMyFrench achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new PardonMyFrench();
        changeset = mock(Changeset.class);
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGESET, achievement.getType());
    }

    @Test
    public void shouldHaveCountingLimit() {
        assertTrue(Integer.valueOf(10).equals(achievement.getCountingLimit()));
    }

    @Test
    public void shouldHaveBadge() {
        assertNotNull(achievement.getBadge());
    }

    @Test
    public void testFalseWithoutMessage() {
        when(changeset.getMessage()).thenReturn(null);
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testFalseWithNoCurses() {
        when(changeset.getMessage()).thenReturn("just a simple commit message");
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldBeCompleteWords() {
        when(changeset.getMessage()).thenReturn("Godfried needs some hellp");
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldBeTrueWithCurse() {
        when(changeset.getMessage()).thenReturn("just a fucking commit message");
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldIgnoreCase() {
        when(changeset.getMessage()).thenReturn("Testing is a Bitch!");
        assertTrue(achievement.isConditionMet(changeset));
    }
}
