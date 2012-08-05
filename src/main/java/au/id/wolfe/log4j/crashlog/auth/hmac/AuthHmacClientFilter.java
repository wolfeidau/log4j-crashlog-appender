package au.id.wolfe.log4j.crashlog.auth.hmac;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MultivaluedMap;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *
 */
public class AuthHmacClientFilter extends ClientFilter {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private final AuthHmacSecret authHmacSecret;

    public AuthHmacClientFilter(AuthHmacSecret authHmacSecret) {
        this.authHmacSecret = authHmacSecret;
    }

    @Override
    public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {

        // Modify the request
        ClientRequest modifiedClientRequest = null;
        try {
            modifiedClientRequest = modifyRequest(clientRequest);
        } catch (NoSuchAlgorithmException e) {
            throw new ClientHandlerException("Error occurred while building hmac signature.", e);
        } catch (InvalidKeyException e) {
            throw new ClientHandlerException("Error occurred while building hmac signature.", e);
        } catch (UnsupportedEncodingException e) {
            throw new ClientHandlerException("Error occurred while building hmac signature.", e);
        }

        // Call the next client handler in the filter chain and return response
        return getNext().handle(modifiedClientRequest);
    }

    public AuthHmacSecret getAuthHmacSecret() {
        return authHmacSecret;
    }

    private ClientRequest modifyRequest(ClientRequest clientRequest) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {

        SecretKeySpec signingKey = new SecretKeySpec(authHmacSecret.getSecret().getBytes(), HMAC_SHA1_ALGORITHM);

        // get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);

        mac.init(signingKey);

        StringBuilder sb = new StringBuilder();

        updateRequestMethod(sb, clientRequest);
        updateContentMd5(sb, clientRequest);
        updateContentType(sb, clientRequest);
        updateDate(sb, clientRequest);
        updatePrefixedHeaders(sb, clientRequest.getHeaders());
        updateRequestUri(sb, clientRequest);

        Object signature = String.format("%s %s", "AWS", Base64.encode(mac.doFinal(sb.toString().getBytes())));

        clientRequest.getHeaders().put("Authorization", Arrays.asList(signature));

        return clientRequest;
    }

    private void updateContentMd5(StringBuilder mac, ClientRequest clientRequest) throws UnsupportedEncodingException {
        if (clientRequest.getHeaders().containsKey("Content-Md5")) {
            mac.append(String.format("%s\n", clientRequest.getHeaders().get("Content-Md5").get(0)));
        }
    }

    private void updateContentType(StringBuilder mac, ClientRequest clientRequest) throws UnsupportedEncodingException {
        if (clientRequest.getHeaders().containsKey("Content-Type")) {
            mac.append(String.format("%s\n", clientRequest.getHeaders().get("Content-Type").get(0)));
        }
    }

    private void updateDate(StringBuilder mac, ClientRequest clientRequest) throws UnsupportedEncodingException {
        if (clientRequest.getHeaders().containsKey("Date")) {
            mac.append(String.format("%s\n", clientRequest.getHeaders().get("Date").get(0)));
        }
    }

    private static void updateRequestMethod(StringBuilder mac, ClientRequest request) throws UnsupportedEncodingException {
        mac.append(String.format("%s\n", request.getMethod()));
    }

    private static void updateRequestUri(StringBuilder mac, ClientRequest request) throws UnsupportedEncodingException {
        mac.append(request.getURI().getPath());
    }


    private static void updatePrefixedHeaders(StringBuilder mac, MultivaluedMap<String, Object> headers) throws UnsupportedEncodingException {

        // get all the entries out into a list
        List<Map.Entry<String, List<Object>>> entries = new LinkedList(headers.entrySet());

        // sort the entries
        Collections.sort(entries, new Comparator<Map.Entry<String, List<Object>>>() {
            @Override
            public int compare(Map.Entry<String, List<Object>> o1, Map.Entry<String, List<Object>> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        // iterate over the sorted result list
        for (Map.Entry<String, List<Object>> e : entries) {

            List val = e.getValue();
            String header = e.getKey();

            // need to drop the date keyword
            if (header.toLowerCase().equals("content-md5") || header.toLowerCase().equals("content-type") || header.toLowerCase().equals("date")) {
                // NOOP
            } else if (val.size() == 1) {
                mac.append(String.format("%s:%s\n", header.toLowerCase(), val.get(0)));
            } else {
                StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (Object s : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(s);
                }
                mac.append(String.format("%s:%s\n", header.toLowerCase(), sb.toString()));
            }
        }
    }

}
