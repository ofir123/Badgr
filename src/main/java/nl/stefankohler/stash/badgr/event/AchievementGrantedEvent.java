package nl.stefankohler.stash.badgr.event;

import javax.validation.constraints.NotNull;

import nl.stefankohler.stash.badgr.achievements.Achievement;

import com.atlassian.stash.event.StashEvent;
import com.atlassian.stash.user.Person;

/**
 * Event that is raised when a achievement is granted.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
public class AchievementGrantedEvent extends StashEvent {
    private static final long serialVersionUID = -4621175419081074064L;

    private final Achievement achievement;
    private final Person person;

    /**
     * Constructs a new AchievementGrantedEvent.
     * 
     * @param source the Object which publishes the event.
     * @param achievement the Achievement that has been granted.
     * @param person the Person to whom the Achievement is granted.
     */
    public AchievementGrantedEvent(@NotNull Object source, @NotNull Achievement achievement, Person person) {
        super(source);
        this.achievement = achievement;
        this.person = person;
    }

    @NotNull
    public Achievement getAchievement() {
        return achievement;
    }

    @NotNull
    public Person getPerson() {
        return person;
    }

}
