package au.id.wolfe.log4j.crashlog.json;

import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;

/**
 *
 */
public class SnakeCaseJsonProvider extends JacksonJaxbJsonProvider {

    public SnakeCaseJsonProvider() {
        super();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        setMapper(objectMapper);
    }

    public SnakeCaseJsonProvider(Annotations... annotationsToUse) {
        super(annotationsToUse);
    }

    public SnakeCaseJsonProvider(ObjectMapper mapper, Annotations[] annotationsToUse) {
        super(mapper, annotationsToUse);
    }
}
