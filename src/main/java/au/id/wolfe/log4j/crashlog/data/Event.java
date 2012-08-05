package au.id.wolfe.log4j.crashlog.data;

import java.util.Date;

/**
 * "message" : "Some one line error description",
 * "class_name": "RuntimeError",
 * "created_at" : "2012-08-03T12:34:19+10:00"
 */
public class Event {

    String message;
    String className;
    Date createdAt;

    public Event() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
