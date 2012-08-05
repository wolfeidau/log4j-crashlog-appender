package au.id.wolfe.log4j.crashlog;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HttpContext;

/**
 *
 */
public class HttpClientFactory {

    private final HttpClientConfiguration configuration;

    public HttpClientFactory(HttpClientConfiguration configuration) {
        this.configuration = configuration;
    }

    public HttpClient build() {
        return build(new SystemDefaultDnsResolver());
    }

    public HttpClient build(DnsResolver resolver) {
        final BasicHttpParams params = createHttpParams();
        final ClientConnectionManager manager = createConnectionManage(SchemeRegistryFactory.createDefault(), resolver);
        final DefaultHttpClient client = new DefaultHttpClient(manager, params);
        setStrategiesForClient(client);

        return client;
    }

    private void setStrategiesForClient(DefaultHttpClient client) {

        final long keepAlive = configuration.getKeepAlive();

        if (keepAlive == 0) {
            client.setReuseStrategy(new NoConnectionReuseStrategy());
        } else {
            client.setReuseStrategy(new DefaultConnectionReuseStrategy());

            //either keep alive based on response header Keep-Alive,
            //or if the server can keep a persistent connection (-1), then override based on client's configuration
            client.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    final long duration = super.getKeepAliveDuration(response, context);
                    return (duration == -1) ? keepAlive : duration;
                }
            });
        }
    }

    /**
     * Map the parameters in HttpClientConfiguration to a BasicHttpParams object
     *
     * @return a BasicHttpParams object from the HttpClientConfiguration
     */
    protected BasicHttpParams createHttpParams() {
        final BasicHttpParams params = new BasicHttpParams();

        // disable cookies
        params.setParameter(AllClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);

        final Integer timeout = (int) configuration.getTimeout();
        params.setParameter(AllClientPNames.SO_TIMEOUT, timeout);

        final Integer connectionTimeout = (int) configuration.getConnectionTimeout();
        params.setParameter(AllClientPNames.CONNECTION_TIMEOUT, connectionTimeout);

        params.setParameter(AllClientPNames.TCP_NODELAY, Boolean.TRUE);
        params.setParameter(AllClientPNames.STALE_CONNECTION_CHECK, Boolean.FALSE);

        return params;
    }

    protected ClientConnectionManager createConnectionManage(SchemeRegistry registry, DnsResolver resolver) {
        //final long ttl = configuration.getTimeToLive()

        final PoolingClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager();

        clientConnectionManager.setDefaultMaxPerRoute(configuration.getMaxConnectionsPerRoute());
        clientConnectionManager.setMaxTotal(configuration.getMaxConnections());
        return clientConnectionManager;
    }
}
