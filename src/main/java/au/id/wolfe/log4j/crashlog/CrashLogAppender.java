package au.id.wolfe.log4j.crashlog;

import au.id.wolfe.log4j.crashlog.auth.hmac.AuthHmacClientFilter;
import au.id.wolfe.log4j.crashlog.auth.hmac.AuthHmacSecret;
import au.id.wolfe.log4j.crashlog.data.*;
import com.sun.jersey.api.client.Client;
import org.apache.log4j.spi.LoggingEvent;

import javax.ws.rs.core.MediaType;

/**
 * Log4J appender for the http://crashlog.io service.
 */
public class CrashLogAppender extends org.apache.log4j.AppenderSkeleton
        implements org.apache.log4j.Appender {

    private String crashLogURL = "https://stdin.crashlog.io/notify";
    private String crashAuthId;
    private String crashAuthKey;

    private JerseyClientFactory jerseyClientFactory = null;

    public String getCrashLogURL() {
        return crashLogURL;
    }

    public void setCrashLogURL(String crashLogURL) {
        this.crashLogURL = crashLogURL;
    }

    public String getCrashAuthId() {
        return crashAuthId;
    }

    public void setCrashAuthId(String crashAuthId) {
        this.crashAuthId = crashAuthId;
    }

    public String getCrashAuthKey() {
        return crashAuthKey;
    }

    public void setCrashAuthKey(String crashAuthKey) {
        this.crashAuthKey = crashAuthKey;
    }

    @Override
    protected void append(LoggingEvent event) {

        CrashLogRecord crashLogRecord = new CrashLogRecord();

        crashLogRecord.setPayload(new Payload());

        // translate the event location information
        buildEvent(crashLogRecord, event);
        buildNotifier(crashLogRecord, event);
        buildBackTrace(crashLogRecord, event);

        Client jerseyClient = getClient();

        jerseyClient.addFilter(new AuthHmacClientFilter(new AuthHmacSecret()
                .accessId(crashAuthId)
                .secret(crashAuthKey)));

        jerseyClient.resource(crashLogURL).type(MediaType.APPLICATION_JSON)
                .post(CrashLogResponse.class, crashLogRecord);
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