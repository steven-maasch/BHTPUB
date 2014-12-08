package org.dieschnittstelle.jee.esa.wsv;

import java.util.ArrayList;
import java.util.List;

import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// Fragen:
// 1. Auf welche Ressourcen wird zugegriffen?
// 2. Welche HTTP Methoden sind geeignet, um die Art des Zugriffs auszudruecken?
// 3. Wie sollen Argumente der Java-Methoden uebermittelt werden?
@Path("/resteasy/touchpoints")
@Consumes({"application/json"})
@Produces({MediaType.APPLICATION_JSON})
public interface ITouchpointCRUDWebService {
	
	// GET /touchpoints: BODY
	@GET
	public List<StationaryTouchpoint> readAllTouchpoints();
	
	// GET /touchpoints/{id}: BODY
	@GET
	@Path("/{id}")
	public StationaryTouchpoint readTouchpoint(@PathParam("id") int id);
	
	// POST /touchpoints: BODY
	@POST
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint); 
	
//	DELETE  /touchpoints/{id}: 
	@DELETE
	@Path("/{id}")	
	public boolean deleteTouchpoint(@PathParam("id") int id); 
		
	/*
	 * UE JRS1: add a new annotated method for using the updateTouchpoint functionality of TouchpointCRUDExecutor and implement it
	 */
// PUT /touchpoints/{id}
	@PUT
	@Path("/{id}")
	public StationaryTouchpoint updateTouchpoint(@PathParam("id") int id,StationaryTouchpoint tp);
	
}
