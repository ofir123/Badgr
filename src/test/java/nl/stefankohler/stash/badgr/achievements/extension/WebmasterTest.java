package nl.stefankohler.stash.badgr.achievements.extension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;
import com.atlassian.stash.content.Path;

@RunWith(MockitoJUnitRunner.class)
public class WebmasterTest {

    private Webmaster achievement;
    private Path path;

    @Before
    public void setup() {
        achievement = new Webmaster();

        path = mock(Path.class);
        when(path.getExtension()).thenReturn("html");
    }

    @Test
    public void shouldBeChangeset() {
        assertEquals(AchievementType.CHANGE, achievement.getType());
    }

    @Test
    public void shouldHaveBadge() {
        assertNotNull(achievement.getBadge());
    }

    @Test
    public void testAdd() {
        Change change = mock(Change.class);
        when(change.getType()).thenReturn(ChangeType.ADD);
        when(change.getPath()).thenReturn(path);

        assertTrue(achievement.isConditionMet(change));
    }

    @Test
    public void testCopy() {
        Change change = mock(Change.class);
        when(change.getType()).thenReturn(ChangeType.COPY);
        when(change.getPath()).thenReturn(path);

        assertFalse(achievement.isConditionMet(change));
    }

    @Test
    public void testCountingLimit() {
        assertTrue(achievement.getCountingLimit().equals(200));
    }
}
