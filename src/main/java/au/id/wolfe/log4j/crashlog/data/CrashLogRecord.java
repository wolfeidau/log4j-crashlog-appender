package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Root element of the CrashLog request.
 */
public class CrashLogRecord implements Serializable {

    @JsonProperty("payload")
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
