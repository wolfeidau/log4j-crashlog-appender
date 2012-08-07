package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 *
 */
public class Notifier implements Serializable {

    @JsonProperty("name")
    String name;

    @JsonProperty("version")
    String version;

    public Notifier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
