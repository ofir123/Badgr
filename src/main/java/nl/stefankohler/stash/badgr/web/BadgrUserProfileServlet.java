package nl.stefankohler.stash.badgr.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.stefankohler.stash.badgr.web.soy.SoyAchievementService;

import com.atlassian.plugin.webresource.WebResourceManager;
import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.google.common.collect.ImmutableMap;

public class BadgrUserProfileServlet extends HttpServlet {

    private final SoyTemplateRenderer soyTemplateRenderer;
    private final WebResourceManager webResourceManager;
    private final UserService userService;
    private final SoyAchievementService achievementService;
    private final StashAuthenticationContext authenticationContext;
    private final NavBuilder navBuilder;

    public BadgrUserProfileServlet(SoyTemplateRenderer soyTemplateRenderer, WebResourceManager webResourceManager, UserService userService,
            SoyAchievementService achievementService, StashAuthenticationContext authenticationContext, NavBuilder navBuilder) {
        this.soyTemplateRenderer = soyTemplateRenderer;
        this.webResourceManager = webResourceManager;
        this.userService = userService;
        this.achievementService = achievementService;
        this.authenticationContext = authenticationContext;
        this.navBuilder = navBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (authenticationContext.isAuthenticated()) {
            // Get userSlug from path
            String pathInfo = req.getPathInfo();

            String[] pathParts = pathInfo.substring(1).split("/");
            if (pathParts.length != 2 || !"users".equalsIgnoreCase(pathParts[0])) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String userSlug = pathParts[1];
            StashUser user = userService.getUserBySlug(userSlug);

            if (user == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            render(resp, "badgr.user.achievements",
                    ImmutableMap.<String, Object> of("user", user, "achievements", achievementService.findSoyAchievementsForUser(user, null)));
        } else {
            resp.sendRedirect(navBuilder.buildAbsolute());
        }
    }

    private void render(HttpServletResponse resp, String templateName, Map<String, Object> data) throws IOException, ServletException {
        webResourceManager.requireResourcesForContext("nl.stefankohler.stash.badgr.achievements.user");
        resp.setContentType("text/html;charset=UTF-8");
        try {
            soyTemplateRenderer.render(resp.getWriter(), "nl.stefankohler.stash.badgr:badgr-serverside-soy", templateName, data);
        } catch (SoyException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw (IOException) cause;
            }
            throw new ServletException(e);
        }
    }

}
