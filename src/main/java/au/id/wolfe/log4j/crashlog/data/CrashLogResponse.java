package au.id.wolfe.log4j.crashlog.data;

/**
 *
 */
public class CrashLogResponse {

    private String id;

    public CrashLogResponse() {
    }

    public CrashLogResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
