package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.stash.content.Changeset;

public class VerboseTest {

    private Verbose achievement;
    private Changeset changeset;

    @Before
    public void setup() {
        achievement = new Verbose();
        changeset = mock(Changeset.class);
    }

    @Test
    public void testLongMessage() {
        String message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin augue urna,"
                + "dictum eu ultrices sed, euismod a ante. Nulla fermentum quam enim, at"
                + "convallis odio. Etiam euismod purus quis nibh dictum commodo. Pellentesque"
                + "ultrices suscipit libero, ut semper turpis sodales ut. Pellentesque habitant"
                + "morbi tristique senectus et netus et malesuada fames ac turpis egestas."
                + "Donec venenatis dui justo. Morbi justo arcu, consectetur ut viverra non,"
                + "pretium vitae ante. Fusce elementum, nulla vitae cursus porta, odio odio"
                + "tempus neque, sed scelerisque urna nisl interdum leo. Vivamus vestibulum"
                + "tempor enim. Ut facilisis rutrum dolor quis aliquam. Suspendisse nunc diam,"
                + "luctus sed ullamcorper at, lacinia in leo. Lorem ipsum dolor sit amet,"
                + "consectetur adipiscing elit. Proin augue urna, dictum eu ultrices sed,"
                + "euismod a ante. Nulla fermentum quam enim, at convallis odio. Etiam euismod"
                + "purus quis nibh dictum commodo. Pellentesque ultrices suscipit libero, ut"
                + "semper turpis sodales ut. Pellentesque habitant morbi tristique senectus "
                + "et netus et malesuada fames ac turpis egestas. Donec venenatis dui justo."
                + "Morbi justo arcu, consectetur ut viverra non, pretium vitae ante. Fusce"
                + "elementum, nulla vitae cursus porta, odio odio tempus neque, sed scelerisque"
                + "urna nisl interdum leo. Vivamus vestibulum tempor enim. Ut facilisis rutrum"
                + "dolor quis aliquam. Suspendisse nunc diam, luctus sed ullamcorper at";

        when(changeset.getMessage()).thenReturn(message);
        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testShortMessage() {
        when(changeset.getMessage()).thenReturn("");
        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void testNoMessage() {
        when(changeset.getMessage()).thenReturn(null);
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
        assertTrue(Integer.valueOf(1).equals(achievement.getCountingLimit()));
    }

}
