package nl.stefankohler.stash.badgr;

import nl.stefankohler.stash.badgr.achievements.Achievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * The AchievementRegisterPostProcess is triggered by the auto-scanning
 * of  Achievement annotated classes. If the new bean is of the type
 * {@link Achievement} then it will be registered by the {@link AchievementManager}.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Component
public class AchievementRegisterPostProcessor implements BeanPostProcessor {

    private final AchievementManager achievementManager;

    /**
     * Constructs the PostProcessor.
     * @param achievementManager the {@link AchievementManager} to register the Achievements to.
     */
    @Autowired
    public AchievementRegisterPostProcessor(AchievementManager achievementManager) {
        this.achievementManager = achievementManager;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof Achievement) {
            achievementManager.register((Achievement) bean);
        }
        return bean;
    }
}
