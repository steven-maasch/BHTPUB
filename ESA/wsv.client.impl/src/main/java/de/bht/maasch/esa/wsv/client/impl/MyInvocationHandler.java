package de.bht.maasch.esa.wsv.client.impl;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.jee.esa.crm.entities.StationaryTouchpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyInvocationHandler implements InvocationHandler {

	private final Class<?> intfs;
	
	private final String url;
	
	private final String commonPath;
	
	private final ObjectMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(MyInvocationHandler.class);
	
	public MyInvocationHandler(final Class<?> intfs, final String url) {
		this.intfs = intfs;
		this.url = url;
		this.commonPath = intfs.getAnnotation(Path.class).value();
		this.mapper = new ObjectMapper();
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		logger.debug(">> invoke()");
		logger.debug("method = {}", method);
		logger.debug("args = {}", Arrays.toString(args));
		
		if (method.getName() == "toString") {
			logger.debug("<< invoke()");
			return intfs.toString();
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			final Method intfsMethod = intfs.getDeclaredMethod(method.getName(), method.getParameterTypes());
			logger.debug("Interface method = {}", intfsMethod);
			final Annotation[] methodAnnotations = intfsMethod.getDeclaredAnnotations();
			logger.debug("Interface method annotations = {}", Arrays.toString(methodAnnotations));
			
			// create the full path
			final StringBuilder completePath = new StringBuilder();
			completePath.append(url);
			completePath.append(commonPath);
			
			if (intfsMethod.getAnnotation(Path.class) != null) {
				Integer i = getIndexInFirstArrayForTargetValue(intfsMethod.getParameterAnnotations(), PathParam.class);
				if (i != null) {
					completePath.append("/");
					completePath.append(args[i]);
				}
			}
			
			logger.debug("Request path = {}", completePath.toString());

			// execute requests
			if (intfsMethod.getAnnotation(GET.class) != null) {
				logger.debug("GET annotation present.");
				
				final HttpGet httpGet = new HttpGet(completePath.toString());
				final CloseableHttpResponse response = httpClient.execute(httpGet);
				
				try {
				    HttpEntity entity = response.getEntity();
				    if (entity != null) {
				    	mapper.readValue(entity.getContent(), StationaryTouchpoint.class);
				        InputStream instream = entity.getContent();
				        int byteOne = instream.read();
				        int byteTwo = instream.read();
				    }
				} finally {
				    response.close();
				}
			} else if (intfsMethod.getAnnotation(POST.class) != null) {
				logger.debug("POST annotation present.");
			} else if (intfsMethod.getAnnotation(PUT.class) != null) {
				logger.debug("PUT annotation present.");
			} else if (intfsMethod.getAnnotation(DELETE.class) != null) {
				logger.debug("DELETE annotation present.");
			}
			
			return null;
		} catch(NoSuchMethodException e) {
			logger.error("Method {} not declared in {}.", method.getName(), intfs);
			throw new IllegalArgumentException(e);
		}
	}
	
	private <T extends Annotation, U extends Annotation> Integer getIndexInFirstArrayForTargetValue(T[][] multiArr, Class<U> targetValue) {
		for (int i = 0; i < multiArr.length; ++i) {
			for (int j = 0; j < multiArr[i].length; j++) {
				final T obj = multiArr[i][j];
				if (obj.annotationType() == targetValue) {
					return i;
				}
			}
		}
		return null;
	}

}
