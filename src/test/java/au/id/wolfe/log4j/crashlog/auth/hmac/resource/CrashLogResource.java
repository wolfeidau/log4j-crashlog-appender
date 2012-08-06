package au.id.wolfe.log4j.crashlog.auth.hmac.resource;

import au.id.wolfe.log4j.crashlog.data.CrashLogRecord;
import au.id.wolfe.log4j.crashlog.data.CrashLogResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/crashlog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CrashLogResource {

    @POST
    public CrashLogResponse submitCrashLog(CrashLogRecord crashLogRecord){

        return new CrashLogResponse("123");

    }
}
