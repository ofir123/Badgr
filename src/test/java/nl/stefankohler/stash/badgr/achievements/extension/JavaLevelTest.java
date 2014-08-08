package nl.stefankohler.stash.badgr.achievements.extension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;
import nl.stefankohler.stash.badgr.achievements.extension.JavaLevel1;
import nl.stefankohler.stash.badgr.achievements.extension.JavaLevel2;
import nl.stefankohler.stash.badgr.achievements.extension.JavaLevel3;
import nl.stefankohler.stash.badgr.achievements.extension.JavaLevel4;
import nl.stefankohler.stash.badgr.achievements.extension.JavaLevel5;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;
import com.atlassian.stash.content.Path;

@RunWith(MockitoJUnitRunner.class)
public class JavaLevelTest {

    private JavaLevel1 achievement;
    private Path path;

    @Before
    public void setup() {
        achievement = new JavaLevel1();

        path = mock(Path.class);
        when(path.toString()).thenReturn(".java");
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
        assertTrue(new JavaLevel1().getCountingLimit().equals(25));
        assertTrue(new JavaLevel2().getCountingLimit().equals(100));
        assertTrue(new JavaLevel3().getCountingLimit().equals(1000));
        assertTrue(new JavaLevel4().getCountingLimit().equals(5000));
        assertTrue(new JavaLevel5().getCountingLimit().equals(10000));
    }
}
