package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * "message" : "Some one line error description",
 * "class_name": "RuntimeError",
 * "created_at" : "2012-08-03T12:34:19+10:00"
 */
public class Event implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private String className;

    @JsonProperty("timestamp")
    private String createdAt;

    public Event() {
    }

    public Event(String message, String className, String createdAt) {
        this.message = message;
        this.className = className;
        this.createdAt = createdAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
