package au.id.wolfe.log4j.crashlog;

import au.id.wolfe.log4j.crashlog.data.*;
import com.sun.jersey.api.client.Client;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 */
public class CrashLogAppender extends org.apache.log4j.AppenderSkeleton
        implements org.apache.log4j.Appender {

    private String crashLogURL;
    private String crashAuthId;
    private String crashAuthKey;

    private JerseyClientFactory jerseyClientFactory = null;

    @Override
    protected void append(LoggingEvent event) {

        CrashLogRecord crashLogRecord = new CrashLogRecord();

        crashLogRecord.setPayload(new Payload());

        // translate the event location information
        buildEvent(crashLogRecord, event);
        buildNotifier(crashLogRecord, event);
        buildBackTrace(crashLogRecord, event);
    }

    private void buildEvent(CrashLogRecord crashLogRecord, LoggingEvent loggingEvent) {

        Event crashLogEvent = new Event();

        crashLogEvent.setClassName(loggingEvent.getLocationInformation().getClass().getName());
        crashLogEvent.setMessage(loggingEvent.getMessage().toString()); // TODO may need to take into consideration escaping

        crashLogRecord.getPayload().setEvent(crashLogEvent);
    }

    private void buildNotifier(CrashLogRecord crashLogRecord, LoggingEvent loggingEvent) {

        Notifier notifier = new Notifier();
        notifier.setName("log4j-crashlog-appender");
        notifier.setVersion("1.0.0");

        crashLogRecord.getPayload().setNotifier(notifier);

    }

    private void buildBackTrace(CrashLogRecord crashLogRecord, LoggingEvent loggingEvent) {
        if (loggingEvent.getThrowableInformation() != null) {

            Throwable throwable = loggingEvent.getThrowableInformation().getThrowable();

            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                BackTraceEntry backTraceEntry = new BackTraceEntry();
                backTraceEntry.setFile(stackTraceElement.getFileName());
                backTraceEntry.setMethod(stackTraceElement.getMethodName());
                backTraceEntry.setNumber(stackTraceElement.getLineNumber());
                crashLogRecord.getPayload().getBackTrace().add(backTraceEntry);
            }
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    private Client getClient() {

        if (jerseyClientFactory == null) {
            JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
            jerseyClientFactory = new JerseyClientFactory(jerseyClientConfiguration);
        }

        return jerseyClientFactory.build();
    }
}