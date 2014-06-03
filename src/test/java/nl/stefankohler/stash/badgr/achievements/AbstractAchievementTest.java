package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nl.stefankohler.stash.badgr.achievements.extension.JavaScriptGuru;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractAchievementTest {

    private AbstractAchievement achievement;

    @Before
    public void setup() {
        achievement = new AbstractAchievement() {

            @Override
            public boolean isConditionMet(Object subject) {
                return false;
            }

            @Override
            public AchievementType getType() {
                return null;
            }
        };
    }

    @Test
    public void shouldReturnName() {
        assertEquals("achievement.bughunter.name", new BugHunter().getName());
    }

    @Test
    public void shouldReturnDescription() {
        assertEquals("achievement.javascriptguru.description", new JavaScriptGuru().getDescription());
    }

    @Test
    public void defaultCountShouldBeOne() {
        assertTrue(new Integer(1).equals(achievement.getCountingLimit()));
    }

    @Test
    public void shouldBeDefaultBadge() {
        assertEquals("default.png", achievement.getBadge());
    }
}
