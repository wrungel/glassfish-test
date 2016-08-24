package mfrolov.unlock.test.lib;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MyServiceRemoteEjb implements MyServiceRemote {

    @Inject
    private MainLockFilter mainLockFilter;

    public String foo() {
        mainLockFilter.fireEvent();
        return "foo to you";
    }
}
