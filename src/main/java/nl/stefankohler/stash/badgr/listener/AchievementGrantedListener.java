package nl.stefankohler.stash.badgr.listener;

import nl.stefankohler.stash.badgr.NotificationService;
import nl.stefankohler.stash.badgr.event.AchievementGrantedEvent;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.i18n.I18nService;

/**
 * EventListener for the {@link AchievementGrantedEvent}.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
public class AchievementGrantedListener {

    private final NotificationService notificationService;
    private final I18nService i18nService;

    /**
     * Construct a new {@link AchievementGrantedListener}.
     * @param notificationService the {@link NotificationService} to add the notification to.
     * @param i18nService the {@link I18nService} for internationalization of the notification.
     */
    public AchievementGrantedListener(NotificationService notificationService, I18nService i18nService) {
        this.notificationService = notificationService;
        this.i18nService = i18nService;
    }

    /**
     * Stores a notification for the user who just received an Achievement.
     * @param event the AchievementGrantedEvent.
     */
    @EventListener
    public void onAchievementGranted(AchievementGrantedEvent event) {
        String badgeName = i18nService.getText(event.getAchievement().getName(), event.getAchievement().getCode());
        String message = i18nService.getText("badgr.web.notification.achieved", "You have earned the Achievement ''{0}'', Congratz!", badgeName);

        notificationService.store(event.getPerson().getEmailAddress(), message);
    }
}
