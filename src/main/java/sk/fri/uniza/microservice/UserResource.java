/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    /**
     *
     * @param validator
     */
    public UserResource(Validator validator) {
        this.validator = validator;
    }
    private final Validator validator;

    /**
     *
     * @return
     */
    @Path("/user")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)

    @Produces(MediaType.APPLICATION_JSON)
    public User getUser() {
        List<User> list;
        Session session = ServerApplication.buildSessionFactory.openSession();
        session.beginTransaction();
        List<Device> devices = new ArrayList<>();
        DeviceData data1 = new DeviceData("0.5", "31.2.1994:14:52:16");
        DeviceData data2 = new DeviceData("32.2", "31.2.1994:08:04:56");
        DeviceData data3 = new DeviceData("17.2", "31.2.1994:14:52:16");
        DeviceData data4 = new DeviceData("8.2", "31.2.1994:14:52:16");
        DeviceData data5 = new DeviceData("0.5", "31.2.1994:14:52:16");
        DeviceData data6 = new DeviceData("32.2", "31.2.1994:08:04:56");
        DeviceData data7 = new DeviceData("17.2", "31.2.1994:14:52:16");
        DeviceData data8 = new DeviceData("8.2", "31.2.1994:14:52:16");
        DeviceData data9 = new DeviceData("0.5", "31.2.1994:14:52:16");
        DeviceData data10 = new DeviceData("32.2", "31.2.1994:08:04:56");
        DeviceData data11 = new DeviceData("17.2", "31.2.1994:14:52:16");
        DeviceData data12 = new DeviceData("8.2", "31.2.1994:14:52:16");
        Device pokus = new Device(data1);
        devices.add(pokus);
        devices.add(new Device(data2));
        devices.add(new Device(data3));
        devices.add(new Device(data4));
        devices.add(new Device(data5));
        devices.add(new Device(data6));
        devices.add(new Device(data7));
        devices.add(new Device(data8));
        devices.add(new Device(data9));
        devices.add(new Device(data10));
        devices.add(new Device(data11));
        devices.add(new Device(data12));
        List<Device> det = new ArrayList<>();
        det.add(new Device(data4));

        User user = new User(devices);
        User user2 = new User(det);
        user.setUserName("adm");
        user2.setUserName("ide");
        session.save(user);
        session.save(user2);

        session.getTransaction().commit();
        session.close();
        return user;

    }

    /**
     *
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)

    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        List<User> list;
        try (Session session = ServerApplication.buildSessionFactory.openSession()) {
            Transaction beginTransaction = session.beginTransaction();
            Query query = session.createQuery("from User");

            list = query.list();

            session.getTransaction().commit();
            session.close();
        }

        return list;

    }

    /**
     *
     * @param user
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createUser(User user) {

        Session session = ServerApplication.buildSessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User where userName=?");
        User userFromDb = (User) query.setString(0, user.getUserName()).uniqueResult();
        if (userFromDb == null) {
            session.merge(user);
        }

        session.getTransaction().commit();
        session.close();

    }

    /**
     *
     * @param id
     * @throws Exception
     */
    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)

    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") String id) throws Exception {

        Session session = ServerApplication.buildSessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User where id=" + id);
        User user = (User) query.uniqueResult();
        session.delete(user);

        session.getTransaction().commit();
        session.close();

    }

    /**
     *
     * @param id
     * @param user
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("id") String id, User user) {

        Session session = ServerApplication.buildSessionFactory.openSession();
        session.beginTransaction();

        session.update(id, user);
        session.getTransaction().commit();
        session.close();

    }

    /**
     *
     * @param name
     * @return
     */
    @GET
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)

    @Produces(MediaType.APPLICATION_JSON)
    public User getUserByName(@PathParam("name") String name) {

        Session session = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("FROM User where userName=?");
        User user = (User) query.setString(0, name).uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (user == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }
        return user;

    }

    /**
     *
     * @param name
     * @param id
     * @return
     */
    @GET
    @Path("/{name}/device/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device getUserDevice(@PathParam("name") String name, @PathParam("id") String id) {
        boolean contains = false;
        Session session = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("FROM User where userName=?");
        User user = (User) query.setString(0, name).uniqueResult();
        session.getTransaction().commit();
        session.close();

        try {
            Session session3 = ServerApplication.buildSessionFactory.openSession();
            Transaction beginTransaction3 = session3.beginTransaction();
            Query query3 = session3.createQuery("FROM Device where deviceId=?");
            Device deviceFromDb = (Device) query3.setString(0, id).uniqueResult();
            session3.getTransaction().commit();
            session3.close();

            for (Device object : user.getUserDevices()) {
                if (object.getDeviceId() == (deviceFromDb.getDeviceId())) {
                    contains = true;
                    break;
                } else {
                    contains = false;

                }
            }
            if (!contains) {
                throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());

            }
            return deviceFromDb;
        } catch (Exception e) {
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());

    }

    /**
     *
     * @param name
     * @param device
     * @return
     */
    @POST
    @Path("/{name}/device/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Long addUserDevice(@PathParam("name") String name, Device device) {

        Session session = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("FROM User where userName=?");
        User user = (User) query.setString(0, name).uniqueResult();
        session.getTransaction().commit();
        session.close();

        Session session2 = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction2 = session2.beginTransaction();
        List<Device> list;
        list = user.getUserDevices();
        list.add(device);
        user.setUserDevices(list);

        session2.update(user);
        session2.getTransaction().commit();
        session2.close();
        return device.getDeviceId();
    }

    /**
     *
     * @param name
     * @param id
     */
    @DELETE
    @Path("/{name}/device/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUserDevice(@PathParam("name") String name, @PathParam("id") String id) {
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

    /**
     *
     * @param name
     * @param id
     * @param device
     */
    @PUT
    @Path("/{name}/device/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUserDevice(@PathParam("name") String name, @PathParam("id") String id, Device device) {
        boolean contains = false;
        Session session = ServerApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();
        Query query = session.createQuery("FROM User where userName=?");
        User user = (User) query.setString(0, name).uniqueResult();
        session.getTransaction().commit();
        session.close();

        try {
            Session session3 = ServerApplication.buildSessionFactory.openSession();
            Transaction beginTransaction3 = session3.beginTransaction();
            Query query3 = session3.createQuery("FROM Device where deviceId=?");
            Device deviceFromDb = (Device) query3.setString(0, id).uniqueResult();
            session3.getTransaction().commit();
            session3.close();

            for (Device object : user.getUserDevices()) {
                if (object.getDeviceId() == (deviceFromDb.getDeviceId())) {
                    contains = true;
                    object.setData(device.getData());
                    break;
                } else {
                    contains = false;
                }
            }
            if (!contains) {
                throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
            }

            Session session2 = ServerApplication.buildSessionFactory.openSession();
            Transaction beginTransaction2 = session2.beginTransaction();
            session2.update(user);
            session2.getTransaction().commit();
            session2.close();

        } catch (Exception e) {
        }

    }

    /**
     *
     * @param name
     * @param id
     * @param data
     */
    @PUT
    @Path("/{name}/device/{id}/data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUserDeviceData(@PathParam("name") String name, @PathParam("id") String id,DeviceData data) {
        createUserDeviceData(name, id, data);
        
    }

    /**
     *
     * @param name
     * @param id
     * @return
     */
    @GET
    @Path("/{name}/device/{id}/data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceData getUserDeviceData(@PathParam("name") String name, @PathParam("id") String id) {
        return getUserDevice(name, id).getData();

    }

    /**
     *
     * @param name
     * @param id
     */
    @DELETE
    @Path("/{name}/device/{id}/data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUserDeviceData(@PathParam("name") String name, @PathParam("id") String id) {
        boolean contains = false;
        DeviceData data = new DeviceData(null, null);
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
                object.setData(data);
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
    
    /**
     *
     * @param name
     * @param id
     * @param data
     * @return
     */
    @POST
    @Path("/{name}/device/{id}/data")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceData createUserDeviceData(@PathParam("name") String name, @PathParam("id") String id,DeviceData data) {
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
                object.setData(data);
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
        return data;
    }

}
