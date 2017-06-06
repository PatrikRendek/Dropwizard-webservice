/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.views.View;

/**
 *
 * @author P
 */
public class UserView extends View {

    private final User user;

    public User getUser() {
        return user;
    }
    public void deleteDevice(String id) {
        
    }
 

    public UserView(User person) {
        super("user.ftl");
        this.user = person;
    }

}
