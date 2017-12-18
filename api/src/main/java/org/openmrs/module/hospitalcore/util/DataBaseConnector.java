package org.openmrs.module.hospitalcore.util;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.util.DatabaseUpdater;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
@Configuration
public class DataBaseConnector {
	private static Log log = LogFactory.getLog(DataBaseConnector.class);
	@Bean 
	public DriverManagerDataSource dataSource(){
		Properties props = Context.getRuntimeProperties();
		mergeDefaultRuntimePropertiess(props);
		
		String driver = props.getProperty("hibernate.connection.driver_class");
		String username = props.getProperty("hibernate.connection.username");
		String password = props.getProperty("hibernate.connection.password");
		String url = props.getProperty("hibernate.connection.url");
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
	    return dataSource;
	}
	
private static void mergeDefaultRuntimePropertiess(Properties runtimeProperties) {
		
		// loop over runtime properties and precede each with "hibernate" if
		// it isn't already
		Set<Object> runtimePropertyKeys = new HashSet<Object>();
		runtimePropertyKeys.addAll(runtimeProperties.keySet()); // must do it this way to prevent concurrent mod errors
		for (Object key : runtimePropertyKeys) {
			String prop = (String) key;
			String value = (String) runtimeProperties.get(key);
			log.trace("Setting property: " + prop + ":" + value);
			if (!prop.startsWith("hibernate") && !runtimeProperties.containsKey("hibernate." + prop))
				runtimeProperties.setProperty("hibernate." + prop, value);
		}
		
		// load in the default hibernate properties from hibernate.default.properties
		InputStream propertyStream = null;
		try {
			Properties props = new Properties();
			// TODO: This is a dumb requirement to have hibernate in here.  Clean this up
			propertyStream = DatabaseUpdater.class.getClassLoader().getResourceAsStream("hibernate.default.properties");
			OpenmrsUtil.loadProperties(props, propertyStream);
			// add in all default properties that don't exist in the runtime
			// properties yet
			for (Map.Entry<Object, Object> entry : props.entrySet()) {
				if (!runtimeProperties.containsKey(entry.getKey()))
					runtimeProperties.put(entry.getKey(), entry.getValue());
			}
		}
		finally {
			try {
				propertyStream.close();
			}
			catch (Throwable t) {
				// pass
			}
		}
	}
}
