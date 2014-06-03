package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class ThanksForComingInTest {

    private ThanksForComingIn achievement;

    @Before
    public void setup() {
        achievement = new ThanksForComingIn();
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGESET, achievement.getType());
    }

    @Test
    public void shouldHaveBadge() {
        assertNotNull(achievement.getBadge());
    }

    @Test
    public void shouldAlwaysBeTrue() {
        Changeset changeset = mock(Changeset.class);
        assertTrue(achievement.isConditionMet(changeset));
    }

}
