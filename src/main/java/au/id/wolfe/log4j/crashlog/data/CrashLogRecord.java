package au.id.wolfe.log4j.crashlog.data;

/**
 *
 */
public class CrashLogRecord {

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
