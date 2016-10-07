package mfrolov.glassfishtest;

import javax.ejb.Stateless;

@Stateless
public class MyServiceRemoteEjb implements MyServiceRemote {

   public String foo() {
        return "foo to you";
    }
}
