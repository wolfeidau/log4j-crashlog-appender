package au.id.wolfe.log4j.crashlog.auth.hmac;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test based on sample from http://s3.amazonaws.com/doc/s3-developer-guide/RESTAuthentication.html
 */
public class HmacBuilderTest {
    @Test
    public void testBuild() throws Exception {

        HmacBuilder hmacBuilder = new HmacBuilder();

        hmacBuilder.init("OtxrzxIsfpFjA7SwPzILwy8Bw21TLhquhboDYROV");

        assertThat(hmacBuilder.append("PUT\n")
                .append("c8fdb181845a4ca6b8fec737b3581d76\n")
                .append("text/html\n")
                .append("Thu, 17 Nov 2005 18:49:58 GMT\n")
                .append("x-amz-magic:abracadabra\n")
                .append("x-amz-meta-author:foo@bar.com\n")
                .append("/quotes/nelson").build(),
                is("jZNOcbfWmD/A/f3hSvVzXZjM2HU="));
    }

    @Test
    public void testShortBuild() throws Exception {

        HmacBuilder hmacBuilder = new HmacBuilder();

        hmacBuilder.init("secret");

        assertThat(hmacBuilder.appendLine("PUT")
                .appendLine("text/plain")
                .appendLine("blahblah")
                .appendLine("Thu, 10 Jul 2008 03:29:56 GMT")
                .append("/path/to/put").build(),
                is("71wAJM4IIu/3o6lcqx/tw7XnAJs="));
    }

}
