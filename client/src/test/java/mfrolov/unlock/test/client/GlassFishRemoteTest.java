package mfrolov.unlock.test.client;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import mfrolov.unlock.test.lib.MyServiceRemote;

public class GlassFishRemoteTest {

    @Test
    public void test() {
        MyServiceRemote service = RemoteEjbFactory.lookup(MyServiceRemote.class);
        String foo = service.foo();
        MatcherAssert.assertThat(foo, Matchers.is("foo to you"));
    }
}
