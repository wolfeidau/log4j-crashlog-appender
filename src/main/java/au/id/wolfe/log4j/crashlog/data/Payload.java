package au.id.wolfe.log4j.crashlog.data;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Payload {

    private Notifier notifier;
    private Event event;
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
