package org.dieschnittstelle.jee.esa.wsv.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

	private final Class<?> intfs;
	
	private final String url;
	
	public MyInvocationHandler(final Class<?> intfs, final String url) {
		this.intfs = intfs;
		this.url = url;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
