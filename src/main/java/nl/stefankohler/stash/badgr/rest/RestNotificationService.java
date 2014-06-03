package nl.stefankohler.stash.badgr.rest;

import static javax.ws.rs.core.Response.Status.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.stefankohler.stash.badgr.NotificationService;
import nl.stefankohler.stash.badgr.model.AoNotification;

import org.apache.commons.lang.StringUtils;

import com.atlassian.sal.api.user.UserManager;
import com.atlassian.sal.api.user.UserProfile;

/**
 * REST interface for the NotificationService.
 */
@Path("notifications")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class RestNotificationService {

    private UserManager userManager;
    private NotificationService notificationService;

    @GET
    public Response getNotification(@Context HttpServletRequest requestContext) {
        String username = userManager.getRemoteUsername(requestContext);
        if (StringUtils.isNotBlank(username)) {
            UserProfile profile = userManager.getUserProfile(username);
            AoNotification notification = notificationService.getNotification(profile.getEmail());

            if (notification != null) {
                return Response.ok(new RestNotification(notification)).build();
            }
            return Response.status(NO_CONTENT).build();
        }
        return Response.status(UNAUTHORIZED).build();
    }

    @DELETE
    @Path("/{index}")
    public Response deleteLink(@PathParam("index") int index, @Context HttpServletRequest requestContext) throws UnsupportedEncodingException {
        String username = userManager.getRemoteUsername(requestContext);
        if (StringUtils.isNotBlank(username)) {
            UserProfile profile = userManager.getUserProfile(username);
            AoNotification notification = notificationService.getNotification(index);
            if (notification != null && profile.getEmail().equals(notification.getEmail())) {
                notificationService.delete(index);
                return Response.status(OK).build();
            }
        }
        return Response.status(UNAUTHORIZED).build();
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

}
