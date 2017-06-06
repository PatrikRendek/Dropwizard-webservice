/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author P
 */
@Embeddable
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeviceData implements Serializable {

    @Column(name = "DEVICE_DATA_TEMPERATURE")
    private String temperature;

    @Column(name = "DEVICE_DATA_DATE")
    private String date;

    public DeviceData() {
    }

    
    public DeviceData(String temperature, String date) {
        this.temperature = temperature;
        this.date = date;
    }

    @JsonProperty
    public String getTemperature() {
        return temperature;
    }

    @JsonProperty
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @JsonProperty
    public String getDate() {
        return date;
    }

    @JsonProperty
    public void setDate(String date) {
        this.date = date;
    }

}
