package au.id.wolfe.log4j.crashlog.auth.hmac;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

/**
 *
 */
public class AuthHmacClientFilterTest {

    AuthHmacSecret authHmacSecret = new AuthHmacSecret()
            .accessId("44CF9590006BF252F707")
            .secret("OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV");

    AuthHmacClientFilter authHmacClientFilter;

    @Before
    public void setUp() throws Exception {
        authHmacClientFilter = new AuthHmacClientFilter(authHmacSecret);
    }

    @Test
    public void testHandle() throws Exception {

        Client client = Client.create();
        client.addFilter(new LoggingFilter());

        WebResource resource;
        String response;
        resource = client.resource("http://localhost/quotes/nelson");
        resource.addFilter(authHmacClientFilter);
        response = resource.getRequestBuilder()
                .header("X-Amz-Meta-Author", "foo@bar.com")
                .header("X-Amz-Magic", "abracadabra")
                .header("Date", "Thu, 17 Nov 2005 18:49:58 GMT")
                .header("Content-Md5", "c8fdb181845a4ca6b8fec737b3581d76")
                .type(MediaType.TEXT_HTML_TYPE)
                .put(String.class);

        System.out.println(response);
    }


}
