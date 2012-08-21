package au.id.wolfe.log4j.crashlog.auth.hmac;

/**
 * Used by AuthHmacClientFilter to hold the authentication credentials.
 */
public class AuthHmacSecret {

    private String accessId;
    private String secret;

    public AuthHmacSecret accessId(String accessId) {
        setAccessId(accessId);
        return this;
    }

    public AuthHmacSecret secret(String secret) {
        setSecret(secret);
        return this;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
