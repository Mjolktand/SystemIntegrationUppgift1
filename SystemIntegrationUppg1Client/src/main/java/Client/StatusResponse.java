package Client;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "StatusResponse")

public class StatusResponse implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    String datacenter;
    float temperature;
    float targetTemperature;
    float electricityConsumption;
    float electricityPrice;    
    Date time;

    public StatusResponse() {}

    @XmlElement
    public String getDatacenter() 
    {
        return datacenter;
    }

    public void setDatacenter(String datacenter) 
    {
        this.datacenter = datacenter;
    }

    @XmlElement
    public float getTemperature() 
    {
        return temperature;
    }

    public void setTemperature(float temperature) 
    {
        this.temperature = temperature;
    }
    
    @XmlElement
    public float getTargetTemperature() 
    {
        return targetTemperature;
    }

    public void setTargetTemperature(float targetTemperature) 
    {
        this.targetTemperature = targetTemperature;
    }

    @XmlElement
    public float getElectricityConsumption() 
    {
        return electricityConsumption;
    }

    public void setElectricityConsumption(float electricityConsumption) 
    {
        this.electricityConsumption = electricityConsumption;
    }
    
    @XmlElement
    public float getElectricityPrice() 
    {
        return electricityPrice;
    }

    public void setElectricityPrice(float electricityPrice) 
    {
        this.electricityPrice = electricityPrice;
    }
    
    @XmlElement
    public Date getTime() 
    {
        return time;
    }

    public void setTime(Date time) 
    {
        this.time = time;
    }
}
