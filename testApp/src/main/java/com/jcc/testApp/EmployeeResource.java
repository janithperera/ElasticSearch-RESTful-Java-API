package com.jcc.testApp;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jcc.testApp.models.Employee;
import com.jcc.testApp.services.EmployeeService;

@Path("employee")
public class EmployeeResource {
	
	private EmployeeService service = new EmployeeService();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getIt() {
        return service.getAllEmployees();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEmployee(Employee emp) {
    	service.addEmployee(emp);
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEmployee(@PathParam("id")String id, Employee emp) {
    	service.updateEmployee(emp, id);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteEmployee(@PathParam("id")String id) {
    	service.deleteEmployee(id);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("id")String id) {
    	return service.getEmployee(id);
    }
    
    @GET
    @Path("search/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> searchEmployee(@PathParam("keyword")String keyword) {
    	return service.searchEmployee(keyword);
    }
}
