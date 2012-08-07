package au.id.wolfe.log4j.crashlog;

/**
 *
 */
public class JerseyClientConfiguration extends HttpClientConfiguration {

    private boolean gzipEnabled = true;
    private int minThreads = 1;
    private int maxThreads = 10;

    public JerseyClientConfiguration() {
    }

    public boolean isGzipEnabled() {
        return gzipEnabled;
    }

    public void setGzipEnabled(boolean gzipEnabled) {
        this.gzipEnabled = gzipEnabled;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }
}
