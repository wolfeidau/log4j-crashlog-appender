package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 *
 */
public class CrashLogRecord implements Serializable {

    @JsonProperty
    private Payload payload;

    public CrashLogRecord() {
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}
