/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.auth.Auth;
import java.util.List;
import java.util.ListIterator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author P
 */
@Path("view/user/{name}")
@Produces(MediaType.TEXT_HTML)
public class UserViewResource {

    @GET
    public UserView getUser(@Auth LoginClass login,@PathParam("name") String name) {
        Session session = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();

        Query query = session.createQuery("FROM User where userName=?");
        User user = (User) query.setString(0, name).uniqueResult();

        session.getTransaction().commit();
        session.close();
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        return new UserView(user);
    }

    @GET
    @Path("/delete/{id}")
    public void deleteDevice(@PathParam("name") String name, @PathParam("id") String id) {
        boolean contains = false;
        Session session = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("FROM User where userName=?");
        User user = (User) query.setString(0, name).uniqueResult();
        session.getTransaction().commit();
        session.close();

        Session session3 = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction3 = session3.beginTransaction();
        Query query3 = session3.createQuery("FROM Device where deviceId=?");
        Device deviceFromDb = (Device) query3.setString(0, id).uniqueResult();

        session3.getTransaction().commit();
        session3.close();

        Session session2 = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction2 = session2.beginTransaction();
        List<Device> dev;
        ListIterator listIterator = user.getUserDevices().listIterator();
        for (Device object : user.getUserDevices()) {
            if (object.getDeviceId() == (deviceFromDb.getDeviceId())) {
                contains = true;
                user.getUserDevices().remove(object);
                dev = user.getUserDevices();
                user.setUserDevices(dev);
                session2.update(user);

                break;
            } else {
                contains = false;

            }
        }
        if (!contains) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());

        }

        session2.getTransaction().commit();
        session2.close();

    }

}
