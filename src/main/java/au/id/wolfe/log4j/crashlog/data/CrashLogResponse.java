package au.id.wolfe.log4j.crashlog.data;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Root element of the crashlog response.
 */
public class CrashLogResponse {

    @JsonProperty("location_id")
    private String locationId;

    public CrashLogResponse() {
    }

    public CrashLogResponse(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CrashLogResponse that = (CrashLogResponse) o;

        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return locationId != null ? locationId.hashCode() : 0;
    }
}
