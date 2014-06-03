package nl.stefankohler.stash.badgr.rest;

import nl.stefankohler.stash.badgr.model.AoNotification;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class RestNotification {

    @JsonProperty
    private int id;

    @JsonProperty
    private String email;

    @JsonProperty
    private String message;

    public RestNotification(AoNotification delegate) {
        id = delegate.getID();
        email = delegate.getEmail();
        message = delegate.getMessage();
    }

    //-------------------------------------------------------------------------- GETTERS && SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
