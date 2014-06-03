package nl.stefankohler.stash.badgr.model;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Table;

@Table("Achievement")
public interface AoAchievement extends Entity {

    @NotNull
    String getCode();

    void setCode(String code);

    @NotNull
    String getEmail();

    void setEmail(String email);

    @NotNull
    String getChangesetId();

    void setChangesetId(String changesetId);

    @NotNull
    Integer getRepository();

    void setRepository(Integer repository);

    @NotNull
    Date getCreated();

    void setCreated(Date created);

}
