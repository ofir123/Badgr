package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.achievements.AbstractAchievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;

public abstract class ExtensionBasedAchievement extends AbstractAchievement {

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGE;
    }

    @Override
    public boolean isConditionMet(Object subject) {
    	Change change = (Change) subject;
        // For some reason, path.getExtension() didn't work with 4 letters extensions.
    	String path = change.getPath().toString();
        int extensionIndex = path.lastIndexOf('.');
        if ((extensionIndex > -1) && (change.getType().equals(ChangeType.ADD))) {
	    	String extension = path.substring(extensionIndex+1);
            String[] validExtensions = getExtensions();
            for (int i = 0; i < validExtensions.length; i++) {
                if (validExtensions[i].equals(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the file extension for the Achievement to check on.
     */
    public abstract String getExtension();
    
    /**
     * Support for multiple valid extensions, 
     * just wrap the single extension in an array
     * @return string[] array of extensions
     */
    public String[] getExtensions() {
        String[] exts = new String[1];
        exts[0] = getExtension();
        return exts;
    }

}