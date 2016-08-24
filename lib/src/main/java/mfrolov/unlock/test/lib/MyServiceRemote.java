package mfrolov.unlock.test.lib;

import javax.ejb.Remote;

@Remote
public interface MyServiceRemote {
    String foo();
}
