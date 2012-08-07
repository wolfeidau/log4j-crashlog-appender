package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 *
 */
public class BackTraceEntry implements Serializable {

    @JsonProperty("file")
    private String file;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("method")
    private String method;

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
