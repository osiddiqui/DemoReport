package com.demo;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/report")
public class ReportService {
		
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Jersey says: " + msg;
		return Response.status(200).entity(output).build();
	}
	
	
	@GET
	@Path("/client/{clientid}")
    @Produces("text/plain")
    public String getReport(@PathParam("clientid") String clientId) {
        return "Hello World Client: " + clientId;
    }
    

}
