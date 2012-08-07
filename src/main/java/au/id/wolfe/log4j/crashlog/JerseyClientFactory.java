package au.id.wolfe.log4j.crashlog;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sun.jersey.api.client.filter.GZIPContentEncodingFilter;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;
import com.sun.jersey.client.apache4.config.ApacheHttpClient4Config;
import com.sun.jersey.client.apache4.config.DefaultApacheHttpClient4Config;
import org.apache.http.client.HttpClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class JerseyClientFactory {
    private final JerseyClientConfiguration configuration;
    private final HttpClientFactory factory;
    private final ApacheHttpClient4Config config;

    public JerseyClientFactory(JerseyClientConfiguration configuration) {
        this.configuration = configuration;
        this.factory = new HttpClientFactory(configuration);
        this.config = new DefaultApacheHttpClient4Config();
    }

    public JerseyClient build() {
        final HttpClient client = factory.build();

        final ApacheHttpClient4Handler handler = new ApacheHttpClient4Handler(client, null, true);

        config.getSingletons().add(new JacksonJsonProvider());

        final JerseyClient jerseyClient = new JerseyClient(handler, config);

        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("jersey-client-%d")
                .build();

        jerseyClient.setExecutorService(new ThreadPoolExecutor(configuration.getMinThreads(),
                configuration.getMaxThreads(),
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory));

        if (configuration.isGzipEnabled()) {
            jerseyClient.addFilter(new GZIPContentEncodingFilter());
        }

        return jerseyClient;
    }
}
