package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatacenterDAO 
{
    Connection con;

    public DatacenterDAO()
    {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("C:\\Git projekt\\SystemIntegrationUppg1Server\\src\\java\\Server\\settings.properties"));
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            this.con = DriverManager.getConnection(p.getProperty("connectionString"),p.getProperty("name"),p.getProperty("password"));
            } 
        catch (FileNotFoundException ex) {Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);}
        catch (IOException| SQLException | ClassNotFoundException ex) {Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);        }
    }
    
    public List<StatusResponse> getCurrentTemperature()
    {
        List<StatusResponse> SRList = new ArrayList<>();
        System.out.println("Getting temp");
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, temperature FROM datacenters JOIN temperature ON temperature.datacenterId = datacenters.id ORDER BY temperature.Id DESC Limit 3;");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setTemperature(rs.getFloat("temperature"));
                System.out.println(rs.getFloat("temperature"));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SRList;
    }
    
    public List<StatusResponse> getCurrentElectricityConsumption()
    {
        List<StatusResponse> SRList = new ArrayList<>();
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, electricityConsumption_kw FROM datacenters JOIN electricityconsumption ON electricityconsumption.datacenterId = datacenters.id ORDER BY electricityconsumption.Id DESC Limit 3;");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setElectricityConsumption(rs.getFloat("electricityConsumption_kw"));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SRList;
    }
    
    public List<StatusResponse> getCurrentElectricityPrice()
    {
        List<StatusResponse> SRList = new ArrayList<>();
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, electricityPrice_sek_kwh FROM datacenters JOIN electricityprice ON electricityprice.datacenterId = datacenters.id ORDER BY electricityprice.Id DESC Limit 3;");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setElectricityPrice(rs.getFloat("electricityPrice_sek_kwh"));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SRList;
    }
    
    public List<StatusResponse> getCurrentFullStatus()
    {
        List<StatusResponse> SRList = new ArrayList<>();
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, temperature, electricityConsumption_kw, electricityPrice_sek_kwh, time FROM temperature JOIN datacenters ON temperature.datacenterId = datacenters.id JOIN electricityprice ON electricityprice.datacenterId = datacenters.id JOIN electricityconsumption ON electricityconsumption.datacenterId = datacenters.id AND electricityprice.Id = electricityconsumption.electricitypriceId JOIN timestamp ON timestamp.electricityConsumptionId = electricityconsumption.Id AND timestamp.temperatureId = temperature.Id ORDER BY timestamp.Id DESC LIMIT 3");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setTemperature(rs.getFloat("temperature"));
                sr.setElectricityConsumption(rs.getFloat("electricityConsumption_kw"));
                sr.setElectricityPrice(rs.getFloat("electricityPrice_sek_kwh"));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SRList;
    }
    
    public List<StatusResponse> get24HourTemperatureReport()
    {
        List<StatusResponse> SRList = new ArrayList<>();
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, temperature, time FROM datacenters JOIN temperature ON temperature.datacenterId = datacenters.id JOIN timestamp ON timestamp.temperatureId = temperature.id WHERE description = \"A\" ORDER BY time DESC LIMIT 24");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setTemperature(rs.getFloat("temperature"));
                Timestamp timestamp = rs.getTimestamp("time");
                sr.setTime(new java.util.Date(timestamp.getTime()));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, temperature, time FROM datacenters JOIN temperature ON temperature.datacenterId = datacenters.id JOIN timestamp ON timestamp.temperatureId = temperature.id WHERE description = \"B\" ORDER BY time DESC LIMIT 24");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setTemperature(rs.getFloat("temperature"));
                Timestamp timestamp = rs.getTimestamp("time");
                sr.setTime(new java.util.Date(timestamp.getTime()));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, temperature, time FROM datacenters JOIN temperature ON temperature.datacenterId = datacenters.id JOIN timestamp ON timestamp.temperatureId = temperature.id WHERE description = \"C\" ORDER BY time DESC LIMIT 24");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setTemperature(rs.getFloat("temperature"));
                Timestamp timestamp = rs.getTimestamp("time");
                sr.setTime(new java.util.Date(timestamp.getTime()));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SRList;
    }
    
    public List<StatusResponse> get24HourElectricityConsumptionAndCostReport()
    {
        List<StatusResponse> SRList = new ArrayList<>();
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, electricityConsumption_kw, electricityPrice_sek_kwh, time FROM datacenters JOIN electricityconsumption ON electricityconsumption.datacenterId = datacenters.id JOIN electricityprice ON electricityprice.datacenterId = datacenters.id AND electricityprice.id = electricityconsumption.electricitypriceId JOIN timestamp ON timestamp.electricityConsumptionId = electricityconsumption.id WHERE description = \"A\" ORDER BY time DESC LIMIT 24");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setElectricityConsumption(rs.getFloat("electricityConsumption_kw"));
                sr.setElectricityPrice(rs.getFloat("electricityPrice_sek_kwh"));
                Timestamp timestamp = rs.getTimestamp("time");
                sr.setTime(new java.util.Date(timestamp.getTime()));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, electricityConsumption_kw, electricityPrice_sek_kwh, time FROM datacenters JOIN electricityconsumption ON electricityconsumption.datacenterId = datacenters.id JOIN electricityprice ON electricityprice.datacenterId = datacenters.id AND electricityprice.id = electricityconsumption.electricitypriceId JOIN timestamp ON timestamp.electricityConsumptionId = electricityconsumption.id WHERE description = \"B\" ORDER BY time DESC LIMIT 24");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setElectricityConsumption(rs.getFloat("electricityConsumption_kw"));
                sr.setElectricityPrice(rs.getFloat("electricityPrice_sek_kwh"));
                Timestamp timestamp = rs.getTimestamp("time");
                sr.setTime(new java.util.Date(timestamp.getTime()));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT description, electricityConsumption_kw, electricityPrice_sek_kwh, time FROM datacenters JOIN electricityconsumption ON electricityconsumption.datacenterId = datacenters.id JOIN electricityprice ON electricityprice.datacenterId = datacenters.id AND electricityprice.id = electricityconsumption.electricitypriceId JOIN timestamp ON timestamp.electricityConsumptionId = electricityconsumption.id WHERE description = \"C\" ORDER BY time DESC LIMIT 24");
            while(rs.next())
            {
                StatusResponse sr = new StatusResponse();
                sr.setDatacenter(rs.getString("description"));
                sr.setElectricityConsumption(rs.getFloat("electricityConsumption_kw"));
                sr.setElectricityPrice(rs.getFloat("electricityPrice_sek_kwh"));
                Timestamp timestamp = rs.getTimestamp("time");
                sr.setTime(new java.util.Date(timestamp.getTime()));
                SRList.add(sr);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SRList;
    }
    
    public void setNewElectricityPrice(StatusResponse sr)
    {
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE electricityprice SET electricityPrice_sek_kwh = ? WHERE id = ?;");
            int id = 0;
            if(sr.datacenter.equalsIgnoreCase("A"))
            {
                id = 1;
            }
            else if(sr.datacenter.equalsIgnoreCase("B"))
            {
                id = 2;
            }
            else if(sr.datacenter.equalsIgnoreCase("C"))
            {
                id = 3;
            }
            stmt.setFloat(1, sr.getElectricityPrice());
            stmt.setInt(2, id);
            
            int u = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setNewTargetTemperature(StatusResponse sr)
    {
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE targettemperature SET targettemperature = ? WHERE id = ?;");
            int id = 0;
            if(sr.datacenter.equalsIgnoreCase("A"))
            {
                id = 1;
            }
            else if(sr.datacenter.equalsIgnoreCase("B"))
            {
                id = 2;
            }
            else if(sr.datacenter.equalsIgnoreCase("C"))
            {
                id = 3;
            }
            stmt.setFloat(1, sr.getTargetTemperature());
            stmt.setInt(2, id);
            
            int u = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public StatusResponse getHighestConsumingDatacenter()
    {
        StatusResponse sr = new StatusResponse();
        System.out.println("Getting temp");
        try {            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT datacenters.description, sum(electricityConsumption_kw) FROM datacenters.electricityconsumption JOIN datacenters ON datacenters.Id = electricityconsumption.datacenterId GROUP BY datacenters.description ORDER BY sum(electricityconsumption_kw) DESC LIMIT 1;");
            while(rs.next())
            {
                sr.setDatacenter(rs.getString("description")); 
            }            
        } catch (SQLException ex) {
            Logger.getLogger(DatacenterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sr;
    }
}
