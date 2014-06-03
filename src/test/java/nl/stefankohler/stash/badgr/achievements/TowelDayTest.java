package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class TowelDayTest {

    private TowelDay achievement;

    @Before
    public void setup() {
        achievement = new TowelDay();
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
    public void testTowelDay() {
        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2012, 5, 25, 1, 2, 3, 0).toDate());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testTowelDayLongAgo() {
        TowelDay achievement = new TowelDay();

        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2001, 5, 25, 1, 2, 3, 0).toDate());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testOtherDay() {
        TowelDay achievement = new TowelDay();

        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2012, 5, 10, 1, 2, 3, 0).toDate());

        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testOtherMonth() {
        TowelDay achievement = new TowelDay();

        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2012, 6, 25, 1, 2, 3, 0).toDate());

        assertFalse(achievement.isConditionMet(changeset));
    }

}
