package mfrolov.glassfishtest.remoteclient;

import org.junit.Test;

import mfrolov.glassfishtest.MyServiceRemote;

public class GlassFishRemoteTest {

    @Test
    public void test() {
        MyServiceRemote serviceRemote = RemoteEjbFactory.lookup(MyServiceRemote.class);
        String foo = serviceRemote.foo();
        System.out.println(foo);
    }
}
