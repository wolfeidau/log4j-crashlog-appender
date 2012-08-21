package au.id.wolfe.log4j.crashlog.auth.hmac;

import au.id.wolfe.log4j.crashlog.auth.hmac.resource.CrashLogResource;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

/**
 *
 */
public class AuthHmacClientFilterTest extends ResourceTest {

    private final AuthHmacSecret authHmacSecret = new AuthHmacSecret()
            .accessId("44CF9590006BF252F707")
            .secret("OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV");


    @Override
    protected void setUpResources() {
        addResource(new CrashLogResource());
    }

    @Test
    public void testHandle() throws Exception {

/*
        final CrashLogRecord crashLogRecord = new CrashLogRecord();

        crashLogRecord.setPayload(new Payload());
        crashLogRecord.getPayload().setEvent(new Event("Something happened", "MyClass", new Date()));

        client().addFilter(new AuthHmacClientFilter(authHmacSecret));

        assertThat("POST request returns an ID",
                client().resource("/crashlog")
                        .type(MediaType.APPLICATION_JSON)
                        .post(CrashLogResponse.class, crashLogRecord),
                is(new CrashLogResponse("123")));
*/

    }

}