package nl.stefankohler.stash.badgr;

import static com.google.common.base.Preconditions.*;

import java.util.Date;

import javax.validation.constraints.NotNull;

import net.java.ao.DBParam;
import net.java.ao.Query;
import nl.stefankohler.stash.badgr.model.AoNotification;

import com.atlassian.activeobjects.external.ActiveObjects;

/**
 * Default Service for storing and retrieving {@link AoNotification}s from
 * the database.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
public class NotificationService {

    private final ActiveObjects ao;

    /**
     * Constructs the {@link NotificationService}.
     * @param ao the {@link ActiveObjects} for accessing the database.
     */
    public NotificationService(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    /**
     * Stores the message, meant for the user identified by his email address.
     * @param email The email address identifying the StashUser
     * @param message The message of the notification.
     */
    public void store(@NotNull String email, @NotNull String message) {
        checkNotNull(email, "Email must not be null");
        checkNotNull(message, "Message must not be null");

        ao.create(AoNotification.class, //
                new DBParam("EMAIL", email), //
                new DBParam("CREATED", new Date()), //
                new DBParam("MESSAGE", message));
    }

    /**
     * Find the {@link AoNotification} based on it's ID .
     * @param notificationId the ID to search for.
     * @return The {@link AoNotification} if found, <code>null</code> otherwise.
     */
    public AoNotification getNotification(@NotNull int notificationId) {
        checkNotNull(notificationId, "Can't find notification without proper notificationId");
        AoNotification[] notifications = ao.find(AoNotification.class, Query.select().where("ID = ?", notificationId));
        if (notifications.length > 0) {
            return notifications[0];
        }
        return null;
    }

    /**
     * @param email the e-mail identifying the user.
     * @return the oldest Notification for the user.
     */
    public AoNotification getNotification(String email) {
        checkNotNull(email, "Email must not be null");

        AoNotification[] notifications = ao.find(AoNotification.class, Query.select().where("EMAIL = ?", email).order("CREATED ASC").limit(1));
        if (notifications.length > 0) {
            return notifications[0];
        }
        return null;
    }

    /**
     * Deletes a notification based on its ID.
     * @param notificationId the ID of the Notification to delete.
     */
    public void delete(int notificationId) {
        AoNotification notification = getNotification(notificationId);
        if (notification != null) {
            ao.delete(notification);
        }
    }

}
