package au.id.wolfe.log4j.crashlog.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.util.ISO8601DateFormat;

import java.io.IOException;
import java.util.Date;

/**
 * ISO 8601 Date Serializer
 */
public class JsonISO8601DateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(new ISO8601DateFormat().format(value));
    }
}
