package nl.stefankohler.stash.badgr.model;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.Implementation;
import net.java.ao.schema.NotNull;
import nl.stefankohler.stash.badgr.rest.RestNotification;

@Implementation(RestNotification.class)
public interface AoNotification extends Entity {

    @NotNull
    String getEmail();

    void setEmail(String email);

    @NotNull
    Date getCreated();

    void setCreated(Date created);

    @NotNull
    String getMessage();

    void setMessage(String message);

}
