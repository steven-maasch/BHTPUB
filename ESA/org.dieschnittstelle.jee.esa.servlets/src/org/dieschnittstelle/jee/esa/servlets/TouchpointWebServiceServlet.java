package org.dieschnittstelle.jee.esa.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;

import sun.security.krb5.SCDynamicStoreConfig;

public class TouchpointWebServiceServlet extends HttpServlet {

	protected static Logger logger = Logger
			.getLogger(TouchpointWebServiceServlet.class);

	public TouchpointWebServiceServlet() {
		System.err.println("TouchpointWebServiceServlet: constructor invoked\n");
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("doGet()");

		// we assume here that GET will only be used to return the list of all
		// touchpoints

		// obtain the executor for reading out the touchpoints
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			// set the status
			response.setStatus(HttpServletResponse.SC_OK);
			// obtain the output stream from the response and write the list of
			// touchpoints into the stream
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			// write the object
			oos.writeObject(exec.readAllTouchpoints());
			oos.close();
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}
	
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("doDelete()");
		
		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");
		try {
			final String requestPath = req.getPathInfo();
			if (Pattern.matches("^/[\\d]*$", requestPath)) {
				final String requestId = requestPath.substring(1);
				final int tpId = Integer.parseInt(requestId);
				if (exec.deleteTouchpoint(tpId)) {
					resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}
	}

	@Override	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("doPost()");

		// assume POST will only be used for touchpoint creation, i.e. there is
		// no need to check the uri that has been used

		TouchpointCRUDExecutor exec = (TouchpointCRUDExecutor) getServletContext()
				.getAttribute("touchpointCRUD");

		try {
			final ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
			final AbstractTouchpoint reqTouchPoint = (AbstractTouchpoint) ois.readObject();
			ois.close();
			
			final AbstractTouchpoint createdTouchPoint = exec.createTouchpoint(reqTouchPoint);
			
			response.setStatus(HttpServletResponse.SC_CREATED);
			
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			oos.writeObject(createdTouchPoint);
			oos.close();
		
		} catch (Exception e) {
			String err = "got exception: " + e;
			logger.error(err, e);
			throw new RuntimeException(e);
		}

	}
	
}
