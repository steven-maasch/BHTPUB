package org.dieschnittstelle.jee.esa.wsv;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.entities.GenericCRUDExecutor;

public class TouchpointCRUDWebServiceImpl implements ITouchpointCRUDWebService {

	@Context
	private ServletContext servletContext;

	public TouchpointCRUDWebServiceImpl() {
		show("TouchpointCRUDWebServiceImpl(): constructor");
	}

	@Override
	public List<StationaryTouchpoint> readAllTouchpoints() {
		show("readAllTouchpoints()");
		show("servletContext: " + servletContext);

		GenericCRUDExecutor<StationaryTouchpoint> exec = (GenericCRUDExecutor<StationaryTouchpoint>) servletContext
				.getAttribute("touchpointCRUD");

		return exec.readAllObjects();
	}

	@Override
	public StationaryTouchpoint readTouchpoint(int id) {
		show("readTouchpoint()");
		show("servletContext: " + servletContext);

		GenericCRUDExecutor<StationaryTouchpoint> exec = (GenericCRUDExecutor<StationaryTouchpoint>) servletContext
				.getAttribute("touchpointCRUD");

		return exec.readObject(id);
	}

	public static void show(Object content) {
		System.err.println(content + "\n");
	}

	@Override
	public StationaryTouchpoint createTouchpoint(StationaryTouchpoint touchpoint) {
		show("createTouchpoint()");
		show("servletContext: " + servletContext);

		GenericCRUDExecutor<StationaryTouchpoint> exec = (GenericCRUDExecutor<StationaryTouchpoint>) servletContext
				.getAttribute("touchpointCRUD");
		
		return exec.createObject(touchpoint);
	}

	@Override
	public boolean deleteTouchpoint(int id) {
		show("deleteTouchpoint()");
		show("servletContext: " + servletContext);

		GenericCRUDExecutor<StationaryTouchpoint> exec = (GenericCRUDExecutor<StationaryTouchpoint>) servletContext
				.getAttribute("touchpointCRUD");
		
		return exec.deleteObject(id);
	}

	@Override
	public StationaryTouchpoint updateTouchpoint(int id, StationaryTouchpoint tp) {
		show("updateTouchpoint()");
		show("servletContext: " + servletContext);

		GenericCRUDExecutor<StationaryTouchpoint> exec = (GenericCRUDExecutor<StationaryTouchpoint>) servletContext
				.getAttribute("touchpointCRUD");
		
		return exec.updateObject(tp);
	}

}
