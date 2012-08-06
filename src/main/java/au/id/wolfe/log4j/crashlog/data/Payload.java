package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Payload implements Serializable {

    @JsonProperty
    private Notifier notifier;

    @JsonProperty
    private Event event;

    @JsonProperty
    private List<BackTraceEntry> backTrace = new LinkedList<BackTraceEntry>();

    public Payload() {
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<BackTraceEntry> getBackTrace() {
        return backTrace;
    }

}
