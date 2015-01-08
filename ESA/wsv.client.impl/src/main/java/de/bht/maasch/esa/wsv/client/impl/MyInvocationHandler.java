package de.bht.maasch.esa.wsv.client.impl;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

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
				httpGet.setHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());
				
				try (final CloseableHttpResponse response = httpClient.execute(httpGet)) {
				    HttpEntity entity = response.getEntity();
				    logger.debug("Status code = {}", response.getStatusLine().getStatusCode());
				    if (entity != null) {
				    	JaxbAnnotationModule module = new JaxbAnnotationModule();
				    	mapper.registerModule(module);
				    	
				    	logger.debug("<< invoke()");
				    	return mapper.readValue(entity.getContent(), 
				    			TypeFactory.defaultInstance()
				    				.constructType(intfsMethod.getGenericReturnType()));
				    }
				}
			} else if (intfsMethod.getAnnotation(POST.class) != null) {
				logger.debug("POST annotation present.");
				
				final HttpPost httpPost = new HttpPost(completePath.toString());
				httpPost.setHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());
				httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
				
				JaxbAnnotationModule module = new JaxbAnnotationModule();
		    	mapper.registerModule(module);
		    	
		    	try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
		    		// TODO: args[0]
		    		mapper.writeValue(out, args[0]);
					final ByteArrayEntity entity = new ByteArrayEntity(out.toByteArray(), ContentType.APPLICATION_JSON);
			    	httpPost.setEntity(entity);
		    	}
		    	
				try (final CloseableHttpResponse response = httpClient.execute(httpPost)) {
					HttpEntity entity = response.getEntity();
				    logger.debug("Status code = {}", response.getStatusLine().getStatusCode());
				    if (entity != null) {
				    	JaxbAnnotationModule moduleResponse = new JaxbAnnotationModule();
				    	mapper.registerModule(moduleResponse);
				    	
				    	logger.debug("<< invoke()");
				    	return mapper.readValue(entity.getContent(), 
				    			TypeFactory.defaultInstance()
				    				.constructType(intfsMethod.getGenericReturnType()));
				    }
				}
				EntityUtils.consumeQuietly(httpPost.getEntity());
			} else if (intfsMethod.getAnnotation(PUT.class) != null) {
				logger.debug("PUT annotation present.");
				final HttpPut httpPut = new HttpPut(completePath.toString());
				httpPut.setHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());
				httpPut.setHeader("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
				
		    	try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
		    		// TODO: args[1]
		    		mapper.writeValue(out, args[1]);
					final ByteArrayEntity entity = new ByteArrayEntity(out.toByteArray(), ContentType.APPLICATION_JSON);
			    	httpPut.setEntity(entity);
		    	}
		    	
				try (final CloseableHttpResponse response = httpClient.execute(httpPut)) {
					HttpEntity entity = response.getEntity();
				    logger.debug("Status code = {}", response.getStatusLine().getStatusCode());
				    if (entity != null) {
				    	JaxbAnnotationModule moduleResponse = new JaxbAnnotationModule();
				    	mapper.registerModule(moduleResponse);
				    	
				    	logger.debug("<< invoke()");
				    	return mapper.readValue(entity.getContent(), 
				    			TypeFactory.defaultInstance()
				    				.constructType(intfsMethod.getGenericReturnType()));
				    }
				}
				EntityUtils.consumeQuietly(httpPut.getEntity());
			} else if (intfsMethod.getAnnotation(DELETE.class) != null) {
				logger.debug("DELETE annotation present.");
				
				final HttpDelete httpDelete = new HttpDelete(completePath.toString());
				
				try (final CloseableHttpResponse response = httpClient.execute(httpDelete)) {
					final int statusCode = response.getStatusLine().getStatusCode();
					logger.debug("Status code = {}", statusCode);
					return statusCode >= 200 && statusCode < 300;
				}
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
