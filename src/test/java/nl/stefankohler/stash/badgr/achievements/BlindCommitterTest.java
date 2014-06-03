package nl.stefankohler.stash.badgr.achievements;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

import nl.stefankohler.stash.badgr.StashUserMetaDataService;
import nl.stefankohler.stash.badgr.model.StashUserMetaData;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.atlassian.stash.event.RepositoryPushEvent;
import com.atlassian.stash.repository.RefChange;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashUser;

@RunWith(MockitoJUnitRunner.class)
public class BlindCommitterTest {

    @Mock
    private StashUserMetaDataService metaDataService;
    private Repository repository;
    private StashUserMetaData stashUserMetaData;
    private Collection<RefChange> refChanges;

    @Before
    public void setup() {
        repository = mock(Repository.class);
        stashUserMetaData = mock(StashUserMetaData.class);

        when(metaDataService.find(any(StashUser.class), eq(StashUserMetaData.LAST_PULL_KEY))).thenReturn(stashUserMetaData);

        refChanges = new ArrayList<RefChange>();
    }

    @Test
    public void testLastPullFiveDaysAgo() {
        BlindCommitter achievement = new BlindCommitter(metaDataService);
        when(stashUserMetaData.getMetaValue()).thenReturn(new DateTime().minusDays(5).toDate().toString());
        assertTrue(achievement.isConditionMet(new RepositoryPushEvent(this, repository, refChanges)));
    }

    @Test
    public void testLastPullThreeDaysAgo() {
        BlindCommitter achievement = new BlindCommitter(metaDataService);
        when(stashUserMetaData.getMetaValue()).thenReturn(new DateTime().minusDays(3).toDate().toString());
        assertTrue(achievement.isConditionMet(new RepositoryPushEvent(this, repository, refChanges)));
    }

    @Test
    public void testLastPullTwoDaysAgo() {
        BlindCommitter achievement = new BlindCommitter(metaDataService);
        when(stashUserMetaData.getMetaValue()).thenReturn(new DateTime().minusDays(2).toDate().toString());
        assertFalse(achievement.isConditionMet(new RepositoryPushEvent(this, repository, refChanges)));
    }

    @Test
    public void testLastPullInFuture() {
        BlindCommitter achievement = new BlindCommitter(metaDataService);
        when(stashUserMetaData.getMetaValue()).thenReturn(new DateTime().plusDays(2).toDate().toString());
        assertFalse(achievement.isConditionMet(new RepositoryPushEvent(this, repository, refChanges)));
    }

}
