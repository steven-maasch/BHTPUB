package de.bht.maasch.esa.wsv.client.intfs;

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

import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

@Path("/resteasy/touchpoints")
@Consumes({"application/json"})
@Produces({MediaType.APPLICATION_JSON})
public interface ITouchpointCRUDWebService {

	@GET
	public List<StationaryTouchpoint> readAllTouchpoints();
	
	@GET
	@Path("/{id}")
	public StationaryTouchpoint readTouchpoint(@PathParam("id") int id);
	
	@POST
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint); 
	
	@DELETE
	@Path("/{id}")	
	public boolean deleteTouchpoint(@PathParam("id") int id); 

	@PUT
	@Path("/{id}")
	public StationaryTouchpoint updateTouchpoint(@PathParam("id") int id,StationaryTouchpoint tp);
	
}
