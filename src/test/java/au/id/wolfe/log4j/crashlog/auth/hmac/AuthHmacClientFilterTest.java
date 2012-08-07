package au.id.wolfe.log4j.crashlog.auth.hmac;

import au.id.wolfe.log4j.crashlog.auth.hmac.resource.CrashLogResource;
import au.id.wolfe.log4j.crashlog.data.CrashLogRecord;
import au.id.wolfe.log4j.crashlog.data.CrashLogResponse;
import au.id.wolfe.log4j.crashlog.data.Event;
import au.id.wolfe.log4j.crashlog.data.Payload;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

        final CrashLogRecord crashLogRecord = new CrashLogRecord();

        crashLogRecord.setPayload(new Payload());
        crashLogRecord.getPayload().setEvent(new Event());
        crashLogRecord.getPayload().getEvent().setClassName("MyClass");
        crashLogRecord.getPayload().getEvent().setCreatedAt(new Date());

        client().addFilter(new AuthHmacClientFilter(authHmacSecret));

        assertThat("POST request returns an ID",
                client().resource("/crashlog")
                        .type(MediaType.APPLICATION_JSON)
                        .post(CrashLogResponse.class, crashLogRecord),
                is(new CrashLogResponse("123")));

    }

}