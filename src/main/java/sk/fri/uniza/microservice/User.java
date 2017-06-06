/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    private Long userId;

    public User(long userId) {
        this.userId = userId;
    }
    private String userName;

    @Column(name = "USER_NAME")
    @JsonProperty
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    private List<Device> userDevices;

    @Deprecated
    public User() {
    }

    public User(List<Device> userDevices) {

        this.userDevices = userDevices;
    }

    public User(List<Device> userDevices, String name) {

        this.userDevices = userDevices;
        this.userName = name;
    }

    public User(String name) {

        this.userName = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    @JsonProperty
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_DEVICE", joinColumns = {
        @JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "DEVICE_ID")})
    public List<Device> getUserDevices() {
        return this.userDevices;
    }

    public void setUserDevices(List<Device> userDevices) {
        this.userDevices = userDevices;
    }

}
