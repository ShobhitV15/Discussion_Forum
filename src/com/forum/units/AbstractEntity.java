package com.forum.units;

import java.util.Date;

import com.forum.util.Utility;

public abstract class AbstractEntity {

    private Date created;
    private long id;

    /**
     * Get the id of this Abstract entity.
     *
     * @return the id of this Abstract entity
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of this Abstract entity.
     *
     * @param id: the id to set for this Abstract entity
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Abstract method to auto-generate an id for the implementing class.
     * This method should be implemented by the concrete subclasses.
     */
    public abstract void autoGenerateId();

    public Date getCreated() {
        return created;
    }

    public void setCreated() {
        this.created = Utility.getCurrentDate();
    }
}
