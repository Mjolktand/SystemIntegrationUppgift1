/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/DatacenterService")

public class DatacenterService extends HttpServlet {

    static DatacenterDAO datacenterDAO = new DatacenterDAO();
    
    @GET
    @Path("/getTemperature")
    @Produces(MediaType.APPLICATION_XML)
    public List<StatusResponse> getCurrentTemperature() throws IOException, ClassNotFoundException
    {
        return datacenterDAO.getCurrentTemperature();
    }
    
    @GET
    @Path("/getConsumption")
    @Produces(MediaType.APPLICATION_XML)
    public List<StatusResponse> getCurrentElectricityConsumption() throws IOException, ClassNotFoundException
    {
        return datacenterDAO.getCurrentElectricityConsumption();
    }
    
    @GET
    @Path("/getElectricityPrice")
    @Produces(MediaType.APPLICATION_XML)
    public List<StatusResponse> getCurrentElectricityPrice() throws IOException, ClassNotFoundException
    {
       return datacenterDAO.getCurrentElectricityPrice();
    }
    
    @GET
    @Path("/getFullStatus")
    @Produces(MediaType.APPLICATION_XML)
    public List<StatusResponse> getFullStatus() throws IOException, ClassNotFoundException
    {
       return datacenterDAO.getCurrentFullStatus();
    }
    
    @GET
    @Path("/get24hTemperatureReport")
    @Produces(MediaType.APPLICATION_XML)
    public List<StatusResponse> get24hTemperatureReport() throws IOException, ClassNotFoundException
    {
       return datacenterDAO.get24HourTemperatureReport();
    }
    
    @GET
    @Path("/get24hElectricityReport")
    @Produces(MediaType.APPLICATION_XML)
    public List<StatusResponse> get24hElectricityReport() throws IOException, ClassNotFoundException
    {
       return datacenterDAO.get24HourElectricityConsumptionAndCostReport();
    }
    
    @POST
    @Path("/setElectricityPrice")
    public void setElectricityPrice(StatusResponse sr) throws IOException, ClassNotFoundException
    {
        datacenterDAO.setNewElectricityPrice(sr);
    }
    
    @POST
    @Path("/setTemperature")
    public void setTargetTemperature(StatusResponse sr) throws IOException, ClassNotFoundException
    {
        datacenterDAO.setNewTargetTemperature(sr);
    }
    
    @GET
    @Path("/getHighestConsumingDatacenter")
    @Produces(MediaType.APPLICATION_XML)
    public StatusResponse getHighestConsumingDatacenter() throws IOException, ClassNotFoundException
    {
       return datacenterDAO.getHighestConsumingDatacenter();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatacenterService</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DatacenterService at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
