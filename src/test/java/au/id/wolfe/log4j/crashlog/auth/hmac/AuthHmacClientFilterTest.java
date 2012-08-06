package au.id.wolfe.log4j.crashlog.auth.hmac;

import au.id.wolfe.log4j.crashlog.data.CrashLogRecord;
import au.id.wolfe.log4j.crashlog.data.CrashLogResponse;
import au.id.wolfe.log4j.crashlog.data.Event;
import au.id.wolfe.log4j.crashlog.data.Payload;
import au.id.wolfe.log4j.crashlog.json.SnakeCaseJsonProvider;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.http.HTTPContainerFactory;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Date;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class AuthHmacClientFilterTest extends JerseyTest {

    private final AuthHmacSecret authHmacSecret = new AuthHmacSecret()
            .accessId("44CF9590006BF252F707")
            .secret("OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV");


    @Override
    protected AppDescriptor configure() {

        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(SnakeCaseJsonProvider.class);

        return new WebAppDescriptor.Builder("au.id.wolfe.log4j.crashlog.auth.hmac.resource")
                .initParam(JSONConfiguration.FEATURE_POJO_MAPPING, "true")
                .clientConfig(cc)
                .build();
    }

    @Test
    public void testHandle() throws Exception {

        final CrashLogRecord crashLogRecord = new CrashLogRecord();

        crashLogRecord.setPayload(new Payload());
        crashLogRecord.getPayload().setEvent(new Event());
        crashLogRecord.getPayload().getEvent().setClassName("MyClass");
        crashLogRecord.getPayload().getEvent().setCreatedAt(new Date());

        client().addFilter(new LoggingFilter());
        client().addFilter(new AuthHmacClientFilter(authHmacSecret));

        CrashLogResponse crashLogResponse = resource().path("/crashlog")
                .getRequestBuilder()
                .type(MediaType.APPLICATION_JSON)
                .post(CrashLogResponse.class, crashLogRecord);

        assertThat(crashLogResponse, notNullValue());

    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new HTTPContainerFactory();
    }
}