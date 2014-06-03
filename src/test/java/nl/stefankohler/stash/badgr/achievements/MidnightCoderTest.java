package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;

import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.content.Changeset;

@RunWith(MockitoJUnitRunner.class)
public class MidnightCoderTest {

    private MidnightCoder achievement;
    private Calendar calendar;

    @Before
    public void setup() {
        achievement = new MidnightCoder();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 00);
    }

    @Test
    public void testIsConditionMet2Pm() {
        Changeset changeset = mock(Changeset.class);

        calendar.set(Calendar.HOUR_OF_DAY, 14);
        when(changeset.getAuthorTimestamp()).thenReturn(calendar.getTime());

        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testIsConditionMet3Am() {
        Changeset changeset = mock(Changeset.class);

        DateTime dateTime = new DateTime(2012, 6, 5, 3, 20, 0, 0);
        when(changeset.getAuthorTimestamp()).thenReturn(dateTime.toDate());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testIsConditionMetTrue() {
        Changeset changeset = mock(Changeset.class);

        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 1);
        when(changeset.getAuthorTimestamp()).thenReturn(calendar.getTime());

        assertTrue(achievement.isConditionMet(changeset));

        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 59);
        when(changeset.getAuthorTimestamp()).thenReturn(calendar.getTime());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testIsConditionMetFalse() {
        Changeset changeset = mock(Changeset.class);

        calendar.set(Calendar.HOUR_OF_DAY, 2);
        when(changeset.getAuthorTimestamp()).thenReturn(calendar.getTime());
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testTimeZones() throws Exception {
        Changeset changeset = mock(Changeset.class);
        DateTime dateTime = new DateTime("2012-10-07T00:00:00.000+10:00");
        when(changeset.getAuthorTimestamp()).thenReturn(dateTime.toDate());
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
        assertTrue(Integer.valueOf(5).equals(achievement.getCountingLimit()));
    }
}
