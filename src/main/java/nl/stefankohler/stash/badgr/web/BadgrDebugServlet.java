package nl.stefankohler.stash.badgr.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.stefankohler.stash.badgr.AchievementManager;
import nl.stefankohler.stash.badgr.achievements.Achievement;
import nl.stefankohler.stash.badgr.model.AoAchievement;
import nl.stefankohler.stash.badgr.model.AoCount;
import nl.stefankohler.stash.badgr.model.StashUserMetaData;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.history.HistoryService;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.Person;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequestImpl;
import com.atlassian.templaterenderer.TemplateRenderer;

public class BadgrDebugServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final TemplateRenderer renderer;
    private final ActiveObjects ao;
    private final AchievementManager achievementManager;
    private final HistoryService historyService;
    private RepositoryService repositoryService;

    public BadgrDebugServlet(TemplateRenderer renderer, ActiveObjects ao, AchievementManager achievementManager, RepositoryService repositoryService,
            HistoryService historyService) {
        super();
        this.renderer = renderer;
        this.ao = ao;
        this.achievementManager = achievementManager;
        this.repositoryService = repositoryService;
        this.historyService = historyService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean devMode = Boolean.parseBoolean(System.getProperty("atlassian.dev.mode", "false"));
        if (!devMode) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "this page is only available in DevMode");
        } else {
            grantAllAchievementsToDefaultPerson();

            response.setContentType("text/html;charset=utf-8");

            AoAchievement[] aoAchievements = ao.find(AoAchievement.class);
            AoCount[] aoCounts = ao.find(AoCount.class);
            StashUserMetaData[] metaDatas = ao.find(StashUserMetaData.class);

            Map<String, Object> context = new HashMap<String, Object>();
            context.put("aoAchievements", Arrays.asList(aoAchievements));
            context.put("aoCounts", Arrays.asList(aoCounts));
            context.put("metaDatas", Arrays.asList(metaDatas));

            renderer.render("/views/debug.vm", context, response.getWriter());
        }
    }

    private void grantAllAchievementsToDefaultPerson() {
        Page<? extends Repository> repositories = repositoryService.findAll(new PageRequestImpl(0, 1));
        Repository repository = repositories.getValues().iterator().next();

        Changeset changeset = historyService.getChangesets(repository, null, null, new PageRequestImpl(0, 1)).getValues().iterator().next();

        List<Achievement> achievements = achievementManager.getAchievements(Achievement.AchievementType.CHANGE);
        for (Achievement achievement : achievements) {
            achievementManager.grantAchievement(achievement, new DefaultPerson(), changeset);
        }
    }

    private static class DefaultPerson implements Person {
        @Override
        public String getName() {
            return "Administrator";
        }

        @Override
        public String getEmailAddress() {
            return "admin@example.com";
        }
    }

}
