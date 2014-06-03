package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import nl.stefankohler.stash.badgr.achievements.Achievement.AchievementType;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.content.MinimalChangeset;
import com.atlassian.stash.history.HistoryService;
import com.atlassian.stash.repository.Repository;

@RunWith(MockitoJUnitRunner.class)
public class RaisingTheDeadTest {

    private final static String CHANGESET_ID = "abcdefg";

    @Mock
    private HistoryService historyService;
    private RaisingTheDead achievement;

    @Before
    public void setup() {
        Changeset changeset = mock(Changeset.class);
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2012, 1, 1, 0, 0, 0, 0).toDate());
        when(historyService.getChangeset(any(Repository.class), eq(CHANGESET_ID))).thenReturn(changeset);

        achievement = new RaisingTheDead(historyService);
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
    public void testIsConditionMet() {
        Changeset changeset = mock(Changeset.class);
        when(changeset.getParents()).thenReturn(getMinimalChangeset());
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2012, 11, 1, 0, 0, 0, 0).toDate());

        assertTrue(achievement.isConditionMet(changeset));
    }

    @Test
    public void testIsConditionNotMet() {
        Changeset changeset = mock(Changeset.class);
        when(changeset.getParents()).thenReturn(getMinimalChangeset());
        when(changeset.getAuthorTimestamp()).thenReturn(new DateTime(2012, 4, 1, 0, 0, 0, 0).toDate());

        assertFalse(achievement.isConditionMet(changeset));
    }

    @Test
    public void shouldBeFalseWithNoParent() {
        Changeset changeset = mock(Changeset.class);
        when(changeset.getParents()).thenReturn(Collections.<MinimalChangeset> emptyList());

        assertFalse(achievement.isConditionMet(changeset));
    }

    private Collection<MinimalChangeset> getMinimalChangeset() {
        Collection<MinimalChangeset> parents = new ArrayList<MinimalChangeset>();
        parents.add(new MinimalChangeset() {
            @Override
            public String getId() {
                return CHANGESET_ID;
            }

            @Override
            public String getDisplayId() {
                return CHANGESET_ID;
            }
        });
        return parents;
    }
}
