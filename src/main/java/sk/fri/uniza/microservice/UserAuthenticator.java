/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 *
 * @author P
 */
public class UserAuthenticator implements Authenticator<BasicCredentials,LoginClass>{

    @Override
    public Optional<LoginClass> authenticate(BasicCredentials c) throws AuthenticationException {
        if ("heslo".equals(c.getPassword())) {
            return Optional.of(new LoginClass());
       }
        else return Optional.absent();
    }
    
}
