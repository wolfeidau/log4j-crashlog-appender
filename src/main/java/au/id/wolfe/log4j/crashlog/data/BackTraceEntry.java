package au.id.wolfe.log4j.crashlog.data;

/**
 *
 */
public class BackTraceEntry {

    String file;
    Integer number;
    String method;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}