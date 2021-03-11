package com.tibco.sb.mongodb;

import java.beans.*;

import com.streambase.sb.operator.parameter.*;

/**
 * A BeanInfo class controls what properties are exposed, add metadata about
 * properties (such as which properties are optional), and access special types
 * of properties that can't be automatically derived via reflection. If a
 * BeanInfo class is present, only the properties explicitly declared in this
 * class will be exposed by StreamBase.
 */
public class MongoBeanInfo extends SBSimpleBeanInfo {

	/*
	 * The order of properties below determines the order they are displayed within
	 * the StreamBase Studio property view.
	 */
	public SBPropertyDescriptor[] getPropertyDescriptorsChecked() throws IntrospectionException {
		SBPropertyDescriptor[] p = {
				new SBPropertyDescriptor("Url", Mongo.class).displayName("Mongo URL").description(""),
				new SBPropertyDescriptor("DB", Mongo.class).displayName("Mongo DB").description(""),
				new SBPropertyDescriptor("collection", Mongo.class).displayName("Collection Name").description(""),
				new SBPropertyDescriptor("connectionTimeOutMs", Mongo.class).displayName("CONNECTION TIME OUT(ms)")
						.description(""),
				new SBPropertyDescriptor("socketTimeOutMs", Mongo.class).displayName("SOCKET TIME OUT(ms)")
						.description(""),
				new SBPropertyDescriptor("serverSelectionTimeoutMs", Mongo.class)
						.displayName("SERVER SELECTION TIMEOUT(ms)").description(""),
				new SBPropertyDescriptor("minConnPool", Mongo.class)
						.displayName("Minimum Connection Pool").description(""),
				new SBPropertyDescriptor("maxConnPool", Mongo.class)
						.displayName("Maximum Connection Pool").description(""),
				new SBPropertyDescriptor("sharedClient", Mongo.class).displayName("Shared Client").description(""),
				new SBPropertyDescriptor("monitorConnection", Mongo.class).displayName("Monitor Connection")
						.description(""), };
		return p;
	}

}
