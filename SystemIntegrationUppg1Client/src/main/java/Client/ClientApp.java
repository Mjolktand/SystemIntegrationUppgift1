package Client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class ClientApp 
{
    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8080/SystemIntegrationUppg1Server/").build());
    
    private static Scanner reader = new Scanner(System.in);
    
    public static void main(String[] args) throws ParseException
    {
        
        int choice;
        
        while(true)
        {
            System.out.println("1. Current temperatures \n2. Current Electricity Consumption \n3. Current Electricity Price \n4. Full Status Report \n5. Set Target Temperature \n6. Set Electricity Price \n7. 24h Temperature Report \n8. 24h Electricity Report \n9. Highest Consuming Datacenter \n");
            choice = reader.nextInt();
            switch (choice) {
                case 1:
                    getCurrentTemperature();
                    break;
                case 2:
                    getCurrentConsumption();
                    break;
                case 3:
                    getElectricityPrice();
                    break;
                case 4:
                    getFullStatusReport();
                    break;
                case 5:
                    setTargetTemperature();
                    break;
                case 6:
                    setElectricityPrice();
                    break;
                case 7:
                    get24hTemperatureReport();
                    break;
                case 8:
                    get24hElectricityReport();
                    break;
                case 9:
                    getHighestConsumingCenter();
                    break;
                default:
                    break;
            }
        }
    }
    
    static public void getCurrentTemperature()
    {
        StatusResponse[] srArray = service.path("rest/DatacenterService/getTemperature").accept(MediaType.APPLICATION_XML).get(StatusResponse[].class);
        List<StatusResponse> srList = Arrays.asList(srArray);
        StatusResponse sr;
        for(int i = srList.size(); i > 0; i--)
        {
            sr = srList.get(i-1);
            System.out.println("\nDatacenter: " + sr.getDatacenter() + "\nTemperature: " + sr.getTemperature()); 
        }
        System.out.println();
    }
    
    static public void getCurrentConsumption()
    {
        StatusResponse[] srArray = service.path("rest/DatacenterService/getConsumption").accept(MediaType.APPLICATION_XML).get(StatusResponse[].class);
        List<StatusResponse> srList = Arrays.asList(srArray);
        StatusResponse sr;
        for(int i = srList.size(); i > 0; i--)
        {
            sr = srList.get(i-1);
            System.out.println("\nDatacenter: " + sr.getDatacenter() + "\nElectricity consumption (kw): " + sr.getElectricityConsumption()); 
        }
        System.out.println();
    }
    
    static public void getElectricityPrice()
    {
        StatusResponse[] srArray = service.path("rest/DatacenterService/getElectricityPrice").accept(MediaType.APPLICATION_XML).get(StatusResponse[].class);
        List<StatusResponse> srList = Arrays.asList(srArray);
        StatusResponse sr;
        for(int i = srList.size(); i > 0; i--)
        {
            sr = srList.get(i-1);
            System.out.println("\nDatacenter: " + sr.getDatacenter() + "\nElectricity price (sek/kwh): " + sr.getElectricityPrice()); 
        }
        System.out.println();
    }
    
    static public void getFullStatusReport()
    {
        StatusResponse[] srArray = service.path("rest/DatacenterService/getFullStatus").accept(MediaType.APPLICATION_XML).get(StatusResponse[].class);
        List<StatusResponse> srList = Arrays.asList(srArray);
        StatusResponse sr;
        for(int i = srList.size(); i > 0; i--)
        {
            sr = srList.get(i-1);
            System.out.println("\nDatacenter: " + sr.getDatacenter() + "\nTemperature: " + sr.getTemperature() + "\nElectricity consumption (kw): " + sr.getElectricityConsumption() + "\nElectricity price (sek/kwh): " + sr.getElectricityPrice() + "\n"); 
        }
    }
    
    static public void setTargetTemperature() throws ParseException
    {
        reader.nextLine();
        String datacenter;
        while(true)
        {
            System.out.println("Enter datacenter: \n");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("A") || input.equalsIgnoreCase("B") || input.equalsIgnoreCase("C"))
            {
                datacenter = input;
                break;
            }
        }
        System.out.println("Enter target temperature: \n");
        float temp = reader.nextFloat();
        StatusResponse sr = new StatusResponse();
        sr.setDatacenter(datacenter);
        sr.setTargetTemperature(temp);
        ClientResponse response = service.path("rest/DatacenterService/setTemperature").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, sr);
    }
    
    static public void setElectricityPrice() throws ParseException
    {
        reader.nextLine();
        String datacenter;
        while(true)
        {
            System.out.println("Enter datacenter: \n");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("A") || input.equalsIgnoreCase("B") || input.equalsIgnoreCase("C"))
            {
                datacenter = input;
                break;
            }
        }
        System.out.println("Enter electricity price (sek/kwh): \n");
        float price = reader.nextFloat();
        StatusResponse sr = new StatusResponse();
        sr.setDatacenter(datacenter);
        sr.setElectricityPrice(price);
        ClientResponse response = service.path("rest/DatacenterService/setElectricityPrice").accept(MediaType.APPLICATION_XML).post(ClientResponse.class, sr);
    }
    
    static public void get24hTemperatureReport()
    {
        StatusResponse[] srArray = service.path("rest/DatacenterService/get24hTemperatureReport").accept(MediaType.APPLICATION_XML).get(StatusResponse[].class);
        List<StatusResponse> srList = Arrays.asList(srArray);
        for(StatusResponse sr : srList)
        {
            System.out.println("\nDatacenter: " + sr.getDatacenter() + "\nTemperature: " + sr.getTemperature() + "\nTime: " + sr.getTime()); 
        }
        float total1 = 0;
        float total2 = 0;
        float total3 = 0;
        float min1 = 1000;
        float min2 = 1000;
        float min3 = 1000;
        float max1 = -1000;
        float max2 = -1000;
        float max3 = -1000;
        for(StatusResponse sr : srList)
        {
            if(sr.datacenter.equalsIgnoreCase("A"))
            {
                total1 = total1 + sr.getTemperature();
                if(sr.getTemperature() < min1)
                {
                    min1 = sr.getTemperature();
                }
                if(sr.getTemperature() > max1)
                {
                    max1 = sr.getTemperature();
                }
            }
            if(sr.datacenter.equalsIgnoreCase("B"))
            {
                total2 = total2 + sr.getTemperature();
                if(sr.getTemperature() < min2)
                {
                    min2 = sr.getTemperature();
                }
                if(sr.getTemperature() > max2)
                {
                    max2 = sr.getTemperature();
                }
            }
            if(sr.datacenter.equalsIgnoreCase("C"))
            {
                total3 = total3 + sr.getTemperature();
                if(sr.getTemperature() < min3)
                {
                    min3 = sr.getTemperature();
                }
                if(sr.getTemperature() > max3)
                {
                    max3 = sr.getTemperature();
                }
            }            
        }
        float average1 = total1 / 24;
        float average2 = total2 / 24;
        float average3 = total3 / 24;
        System.out.println("\nDatacenter A: " + "\nMax Temperature: " + max1 + "\nMin Temperature: " + min1 + "\nAverage Temperature: " + average1);
        System.out.println("\nDatacenter B: " + "\nMax Temperature: " + max2 + "\nMin Temperature: " + min2 + "\nAverage Temperature: " + average2);
        System.out.println("\nDatacenter C: " + "\nMax Temperature: " + max3 + "\nMin Temperature: " + min3 + "\nAverage Temperature: " + average3 + "\n");
    }
    
    static public void get24hElectricityReport()
    {
        StatusResponse[] srArray = service.path("rest/DatacenterService/get24hElectricityReport").accept(MediaType.APPLICATION_XML).get(StatusResponse[].class);
        List<StatusResponse> srList = Arrays.asList(srArray);
        for(StatusResponse sr : srList)
        {
            System.out.println("\nDatacenter: " + sr.getDatacenter() + "\nElectricity consumption (kw): " + sr.getElectricityConsumption() + "\nElectricity price (sek/kwh): " + sr.getElectricityPrice() + "\nTime: " + sr.getTime()); 
        }
        float total1 = 0;
        float total2 = 0;
        float total3 = 0;
        float min1 = 10000;
        float min2 = 10000;
        float min3 = 10000;
        float max1 = 0;
        float max2 = 0;
        float max3 = 0;
        float maxCost1 = 0;
        Date maxCostTime1 = null;
        float maxCost2 = 0;
        Date maxCostTime2 = null;
        float maxCost3 = 0;
        Date  maxCostTime3 = null;
        for(StatusResponse sr : srList)
        {
            if(sr.datacenter.equalsIgnoreCase("A"))
            {
                total1 = total1 + sr.getElectricityConsumption();
                if(sr.getElectricityConsumption() < min1)
                {
                    min1 = sr.getElectricityConsumption();
                }
                if(sr.getElectricityConsumption() > max1)
                {
                    max1 = sr.getElectricityConsumption();
                }
                if((sr.getElectricityConsumption()*sr.getElectricityPrice()) > maxCost1)
                {
                    maxCost1 = sr.getElectricityConsumption()*sr.getElectricityPrice();
                    maxCostTime1 = sr.getTime();
                }
            }
            if(sr.datacenter.equalsIgnoreCase("B"))
            {
                total2 = total2 + sr.getElectricityConsumption();
                if(sr.getElectricityConsumption() < min2)
                {
                    min2 = sr.getElectricityConsumption();
                }
                if(sr.getElectricityConsumption() > max2)
                {
                    max2 = sr.getElectricityConsumption();
                }
                if((sr.getElectricityConsumption()*sr.getElectricityPrice()) > maxCost2)
                {
                    maxCost2 = sr.getElectricityConsumption()*sr.getElectricityPrice();
                    maxCostTime2 = sr.getTime();
                }
            }
            if(sr.datacenter.equalsIgnoreCase("C"))
            {
                total3 = total3 + sr.getElectricityConsumption();
                if(sr.getElectricityConsumption() < min3)
                {
                    min3 = sr.getElectricityConsumption();
                }
                if(sr.getElectricityConsumption() > max3)
                {
                    max3 = sr.getElectricityConsumption();
                }
                if((sr.getElectricityConsumption()*sr.getElectricityPrice()) > maxCost3)
                {
                    maxCost3 = sr.getElectricityConsumption()*sr.getElectricityPrice();
                    maxCostTime3 = sr.getTime();
                }
            }            
        }
        float average1 = total1/24;
        float average2 = total2/24;
        float average3 = total3/24;
        System.out.println("\nDatacenter A: " + "\nMax Electricity Consumption: " + max1 + "\nMin Electricity Consumption: " + min1 + "\nAverage Electricity Consumption: " + average1 + "\nMost Expensive Time: " + maxCostTime1);
        System.out.println("\nDatacenter B: " + "\nMax Electricity Consumption: " + max2 + "\nMin Electricity Consumption: " + min2 + "\nAverage Electricity Consumption: " + average2 + "\nMost Expensive Time: " + maxCostTime2);
        System.out.println("\nDatacenter C: " + "\nMax Electricity Consumption: " + max3 + "\nMin Electricity Consumption: " + min3 + "\nAverage Electricity Consumption: " + average3 + "\nMost Expensive Time: " + maxCostTime3 + "\n");
    }
    
    static public void getHighestConsumingCenter()
    {
        StatusResponse sr = service.path("rest/DatacenterService/getHighestConsumingDatacenter").accept(MediaType.APPLICATION_XML).get(StatusResponse.class);
        System.out.println("\nDatacenter " + sr.getDatacenter() + " had the highest electricity consumption over the last 24 hours.\n");
    }
}


