package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * "message" : "Some one line error description",
 * "class_name": "RuntimeError",
 * "created_at" : "2012-08-03T12:34:19+10:00"
 */
public class Event implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("created_at")
    private Date createdAt;

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
