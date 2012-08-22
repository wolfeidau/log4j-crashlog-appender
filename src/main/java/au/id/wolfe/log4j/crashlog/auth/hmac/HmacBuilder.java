package au.id.wolfe.log4j.crashlog.auth.hmac;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

/**
 * Build the hmac.
 */
public class HmacBuilder {

    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private Mac mac;

    public HmacBuilder() {
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        } catch (GeneralSecurityException e) {
            System.out.println("Unexpected error while creating hash: " + e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    public HmacBuilder init(String secret) {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA1_ALGORITHM);
        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            System.out.println("Unexpected error while initialising key: " + e.getMessage());
            throw new IllegalArgumentException();
        }
        return this;
    }

    public HmacBuilder appendLine(String data) {
        mac.update((String.format("%s\n", data)).getBytes());
        return this;
    }

    public HmacBuilder append(String data) {
        mac.update((data).getBytes());
        return this;
    }

    public String build() {
        byte[] rawHmac = mac.doFinal();
        mac.reset();
        return new String(Base64.encodeBase64(rawHmac));
    }

}
