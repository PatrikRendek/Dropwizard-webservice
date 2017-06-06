/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE")
//@Access(AccessType.FIELD)
public class Device implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "DEVICE_ID")
    private Long deviceId;
    

    @JsonProperty
    public DeviceData getData() {
        return data;
    }

    @JsonProperty
    public void setData(DeviceData data) {
        this.data = data;
    }
    @Embedded
    private DeviceData data;

    public Device() {
    }

    public Device(DeviceData data) {
        this.data = data;

    }

    @JsonProperty
    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceData(DeviceData data) {
        this.data = data;
    }

}
