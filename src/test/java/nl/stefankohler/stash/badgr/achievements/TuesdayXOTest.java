package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class TuesdayXOTest {

    private TuesdayXO achievement;

    @Before
    public void setup() {
        achievement = new TuesdayXO();
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
    public void testTuesdayXO() {
        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2014, 4, 22, 13, 0, 0, 0).toDate());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testTuesdayXOLongAgo() {
    	TuesdayXO achievement = new TuesdayXO();

        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2007, 5, 1, 12, 30, 3, 40).toDate());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testOtherTime() {
    	TuesdayXO achievement = new TuesdayXO();

        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2014, 4, 22, 16, 20, 31, 10).toDate());

        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testOtherDay() {
    	TuesdayXO achievement = new TuesdayXO();

        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2014, 4, 23, 13, 0, 0, 0).toDate());

        assertFalse(achievement.isConditionMet(changeset));
    }

}
