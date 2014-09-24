package nl.stefankohler.stash.badgr.achievements.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;
import com.atlassian.stash.content.Path;

@RunWith(MockitoJUnitRunner.class)
public class CppLevelTest {

    private CppLevel1 achievement;
    private Path path;

    @Before
    public void setup() {
        achievement = new CppLevel1();

        path = mock(Path.class);
        when(path.getExtension()).thenReturn("cpp");
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
        assertTrue(new CppLevel1().getCountingLimit().equals(25));
        assertTrue(new CppLevel2().getCountingLimit().equals(100));
        assertTrue(new CppLevel3().getCountingLimit().equals(1000));
        assertTrue(new CppLevel4().getCountingLimit().equals(5000));
        assertTrue(new CppLevel5().getCountingLimit().equals(10000));
    }
}
