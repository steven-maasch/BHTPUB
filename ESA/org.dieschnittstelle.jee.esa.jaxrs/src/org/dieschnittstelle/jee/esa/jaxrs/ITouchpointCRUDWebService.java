package org.dieschnittstelle.jee.esa.jaxrs;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

@Path("/resteasy/touchpoints")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public interface ITouchpointCRUDWebService {
	
	@GET
	public List<StationaryTouchpoint> readAllTouchpoints();
	
	@POST
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint); 
	
	@DELETE
	@Path("/{touchpointId}")
	public boolean deleteTouchpoint(@PathParam("touchpointId") int id); 
	
	@PUT
	@Path("/{touchpointId}")
	public StationaryTouchpoint updateTouchpoint(@PathParam("touchpointId") int id, StationaryTouchpoint touchpoint);
	
}
