package nl.stefankohler.stash.badgr.model;

import net.java.ao.Entity;
import net.java.ao.schema.NotNull;

public interface AoCount extends Entity {

    @NotNull
    String getCode();

    void setCode(String code);

    @NotNull
    String getEmail();

    void setEmail(String email);

    Integer getAmount();

    void setAmount(Integer amount);

}
