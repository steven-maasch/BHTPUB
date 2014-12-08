package org.dieschnittstelle.jee.esa.jaxrs.client;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.dieschnittstelle.jee.esa.crm.entities.AbstractTouchpoint;
import org.dieschnittstelle.jee.esa.crm.entities.Address;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;
import org.dieschnittstelle.jee.esa.wsv.ITouchpointCRUDWebService;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class ShowTouchpointRESTService {

	protected static Logger logger = Logger
			.getLogger(ShowTouchpointRESTService.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * register jackson as reader and writer for json
		 */
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		// the MessageBodyReader must be added explicitly to the provider
		// factory. Here, we use the jackson implementation
		ResteasyProviderFactory.getInstance().addMessageBodyReader(
				JacksonJaxbJsonProvider.class);
		ResteasyProviderFactory.getInstance().addMessageBodyWriter(
				JacksonJaxbJsonProvider.class);

		/*
		 * create a client for the web service passing the
		 * ITouchpointCRUDWebService interface and url and apache executor to
		 * the proxy factory
		 * 
		 * we do not use the .jaxrs.demo URL because the settings specify the context as .jaxrs - might need to clear the tomcat working dir
		 */
		ITouchpointCRUDWebService serviceClient = 
				ProxyFactory.create(ITouchpointCRUDWebService.class, 
						"http://localhost:8888/org.dieschnittstelle.jee.esa.wsv", 
						new ApacheHttpClient4Executor());

		// 1) read out all touchpoints
		List<StationaryTouchpoint> tps = serviceClient.readAllTouchpoints();
		show("read all: " + tps);

		// 2) delete the touchpoint if there is one
		step();
		if (tps.size() > 0) {
			show("deleted: " + serviceClient.deleteTouchpoint(tps.get(0).getId()));
		}

		// 3) create a new touchpoint
		step();

		Address addr = new Address("Luxemburger Strasse", "10", "13353",
				"Berlin");
		StationaryTouchpoint tp = new StationaryTouchpoint(-1,
				"BHT Verkaufsstand", addr);
		tp = serviceClient.createTouchpoint(tp);
		show("created: " + tp);

		/*
		 * 4) update the touchpoint
		 */
		// change the name
		step();
		tp.setName("BHT Mensa");

		/*
		 * UE JRS1: add a call to the update method, passing tp
		 */
		logger.info("renamed touchpoint with id " + tp.getId() + " to "
				+ tp.getName());
		
		tp = serviceClient.updateTouchpoint(tp.getId(), tp);
		show("updated: " + tp);

	}

	public static void show(Object content) {
		System.err.println(content + "\n");
	}

	public static void step() {
		try {
			System.out.println("/>");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
