package com.ic.data;

import java.util.Date;

/**
 * Enum to indicate Chart Data Interval
 */
public class FRecord {

    private Date creationDateTime = null;
    public FRecord() {
        setCreationDateTime(new Date());
    }

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
