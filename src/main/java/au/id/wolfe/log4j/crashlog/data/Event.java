package au.id.wolfe.log4j.crashlog.data;

import au.id.wolfe.log4j.crashlog.json.JsonISO8601DateSerializer;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
    @JsonSerialize(using = JsonISO8601DateSerializer.class)
    private Date createdAt;

    public Event() {
    }

    public Event(String message, String className, Date createdAt) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
