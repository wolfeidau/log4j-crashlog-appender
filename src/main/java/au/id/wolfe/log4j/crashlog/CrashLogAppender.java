package au.id.wolfe.log4j.crashlog;

import au.id.wolfe.log4j.crashlog.auth.hmac.HmacBuilder;
import au.id.wolfe.log4j.crashlog.data.BackTraceEntry;
import au.id.wolfe.log4j.crashlog.data.CrashLogRecord;
import au.id.wolfe.log4j.crashlog.data.Event;
import au.id.wolfe.log4j.crashlog.data.Notifier;
import au.id.wolfe.log4j.crashlog.data.Payload;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Log4J appender for the http://crashlog.io service.
 */
public class CrashLogAppender extends org.apache.log4j.AppenderSkeleton
        implements org.apache.log4j.Appender {

    private String crashLogURL = "https://stdin.crashlog.io/events";
    private String crashAuthId;
    private String crashAuthKey;
    private String serviceId = "CrashLog";
    private String projectName = "Test";

    //private static final String contentType = "application/json";
    private static final String contentType = "application/json; charset=UTF-8";
    private final static String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";

    private HmacBuilder hmacBuilder = null;

    private HttpClientFactory httpClientFactory = null;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    protected void append(LoggingEvent event) {

        System.out.println("event logged " + event.toString());

        CrashLogRecord crashLogRecord = new CrashLogRecord();

        crashLogRecord.setPayload(new Payload());

        // translate the event location information
        buildEvent(crashLogRecord, event);
        buildNotifier(crashLogRecord, event);
        buildBackTrace(crashLogRecord, event);

        HttpClient httpClient = getClient();

        HttpPost post = new HttpPost(crashLogURL);

        String dateString = getCurrentDate();

        String canonicalRequest = "POST\n" +
                contentType + "\n\n" +
                dateString + "\n" +
                post.getURI().getPath();

/*
        post.addHeader("Authorization", serviceId + " " + crashAuthId + ":" +
                getHmacBuilder().init(crashAuthKey)
                        .appendLine(post.getMethod())
                        .appendLine(contentType)
                        .appendLine(dateString)
                        .append(post.getURI().toString())
                        .build());
*/

        System.out.println(canonicalRequest);

        post.addHeader("Authorization", serviceId + " " + crashAuthId + ":" +
                getHmacBuilder().init(crashAuthKey)
                        .append(canonicalRequest)
                        .build());

        post.addHeader("Date", dateString);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writer(new SimpleDateFormat(DATE_FORMAT));

        try {

            String postBody = mapper.writeValueAsString(crashLogRecord);

            post.setEntity(new StringEntity(postBody, ContentType.APPLICATION_JSON));

            System.out.println(post.toString());

            HttpResponse response = httpClient.execute(post);

            System.out.println("client StatusCode: " + response.getStatusLine().getStatusCode() + " content: " + response.getStatusLine().getReasonPhrase());

        } catch (IOException e) {
            errorHandler.error(e.getMessage(), e, ErrorCode.WRITE_FAILURE);
        } finally {
            post.releaseConnection();
        }

    }

    private void buildEvent(CrashLogRecord crashLogRecord, LoggingEvent loggingEvent) {

        Event crashLogEvent = new Event();

        crashLogEvent.setClassName(loggingEvent.getLocationInformation().getClass().getName());
        crashLogEvent.setMessage(loggingEvent.getMessage().toString()); // TODO may need to take into consideration escaping
        crashLogEvent.setCreatedAt(getDate(loggingEvent.getTimeStamp()));

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

    private String getCurrentDate() {

        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        return df.format(new Date());
    }

    private String getDate(long timestamp) {

        timestamp = timestamp / 1000;

        return String.valueOf(timestamp);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    private HmacBuilder getHmacBuilder() {

        if (hmacBuilder == null) {
            hmacBuilder = new HmacBuilder();
        }

        return hmacBuilder;
    }

    private HttpClient getClient() {

        if (httpClientFactory == null) {
            HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration();
            httpClientFactory = new HttpClientFactory(httpClientConfiguration);
        }

        return httpClientFactory.build();
    }
}