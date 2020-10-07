package org.openmrs.module.hospitalcore.web.controller.apiController;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.openmrs.api.context.Context;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DatabaseConnector {

    public DriverManagerDataSource dataSource() {
        Properties properties = Context.getRuntimeProperties();
        mergeDefaultRuntimePropertiess(properties);

        String username = properties.getProperty("hibernate.connection.username");
        String password = properties.getProperty("hibernate.connection.password");
        String url = properties.getProperty("hibernate.connection.url");

        // System.out.println("username---------------" + username);
        // System.out.println("password---------------" + password);
        // System.out.println("url---------------" + url);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;

    }

    private static void mergeDefaultRuntimePropertiess(Properties runtimeProperties) {
        Set<Object> runtimePropertyKeys = new HashSet<Object>();
        runtimePropertyKeys.addAll(runtimeProperties.keySet()); // must do it this way to prevent concurrent mod errors
        for (Object key : runtimePropertyKeys) {
            String prop = (String) key;
            String value = (String) runtimeProperties.get(key);
            if (!prop.startsWith("hibernate") && !runtimeProperties.containsKey("hibernate." + prop))
                runtimeProperties.setProperty("hibernate." + prop, value);
        }
    }
}
