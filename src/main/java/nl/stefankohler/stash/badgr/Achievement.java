package nl.stefankohler.stash.badgr;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a "Achievement".
 * Such classes are auto-detected with classpath scanning, and registered
 * as Achievement to the {@link AchievementManager}
 *
 * @author Stefan Kohler
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Achievement {

}
