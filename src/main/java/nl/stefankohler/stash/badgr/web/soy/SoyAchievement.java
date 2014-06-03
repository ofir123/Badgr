package nl.stefankohler.stash.badgr.web.soy;

import nl.stefankohler.stash.badgr.achievements.Achievement;

import com.atlassian.plugin.webresource.UrlMode;
import com.atlassian.plugin.webresource.WebResourceUrlProvider;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.repository.Repository;

public class SoyAchievement {

    private final Achievement achievement;
    private final Changeset changeset;
    private final WebResourceUrlProvider urlProvider;
    private final Integer count;
    private final Repository repository;

    public SoyAchievement(Achievement achievement, Changeset changeset, Integer count, Repository repository, WebResourceUrlProvider urlProvider) {
        this.achievement = achievement;
        this.changeset = changeset;
        this.count = (count == null) ? 0 : count;
        this.repository = repository;
        this.urlProvider = urlProvider;
    }

    public String getCode() {
        return achievement.getCode();
    }

    public String getName() {
        return achievement.getName();
    }

    public String getDescription() {
        return achievement.getDescription();
    }

    public Integer getCountingLimit() {
        return achievement.getCountingLimit();
    }

    public Integer getCount() {
        return count;
    }

    public String getBadge() {
        return urlProvider.getStaticPluginResourceUrl("nl.stefankohler.stash.badgr:badgr-resources", "/badges/" + achievement.getBadge(), UrlMode.RELATIVE);
    }

    public Changeset getChangeset() {
        return changeset;
    }

    public Repository getRepository() {
        return repository;
    }

}
