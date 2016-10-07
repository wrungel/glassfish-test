package mfrolov.glassfishtest;

import javax.ejb.Remote;

@Remote
public interface MyServiceRemote {
    String foo();
}
