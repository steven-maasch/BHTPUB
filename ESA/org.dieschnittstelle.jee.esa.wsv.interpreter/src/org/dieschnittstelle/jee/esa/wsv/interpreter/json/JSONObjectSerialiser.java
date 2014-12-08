package org.dieschnittstelle.jee.esa.wsv.interpreter.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.ListenerNotFoundException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.NumericNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.TextNode;
import org.apache.log4j.Logger;

public class JSONObjectSerialiser {

	protected static final Logger logger = Logger
			.getLogger(JSONObjectSerialiser.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	private static final JsonFactory jsonfactory = new JsonFactory(mapper);

	public Object readObject(InputStream is, Type type)
			throws ObjectMappingException {
		try {
			JsonNode data = null;

			// we only make a very rough distinction between maprimitives, arrays (possibly parameterised) and objects
			// parameterised types here
			
			if (type == Boolean.TYPE) {
				data = mapper.readValue(is, BooleanNode.class);
			}
			else if (type == Integer.TYPE || type == Long.TYPE || type == Double.TYPE) {
				data = mapper.readValue(is, NumericNode.class);
			}
			else if (type == String.class) {
				data = mapper.readValue(is, TextNode.class);
			}
			else if (type instanceof ParameterizedType) {
				data = mapper.readValue(is, Collection.class
						.isAssignableFrom((Class) ((ParameterizedType) type)
								.getRawType()) ? ArrayNode.class
						: ObjectNode.class);
			} else {
				data = mapper.readValue(is, Collection.class
						.isAssignableFrom((Class) type) ? ArrayNode.class
						: ObjectNode.class);
			}
			logger.info("read data: " + data);

			return JSONObjectMapper.getInstance().fromjson(data, type);
		} catch (Exception e) {
			throw new ObjectMappingException(e);
		}
	}

	public void writeObject(Object obj, OutputStream os)
			throws ObjectMappingException {
		try {
			JsonGenerator generator = jsonfactory.createJsonGenerator(os,
					JsonEncoding.UTF8);

			generator.writeObject(JSONObjectMapper.getInstance().tojson(obj));
		} catch (Exception e) {
			throw new ObjectMappingException(e);
		}
	}

}
