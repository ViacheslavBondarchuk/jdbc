package jdbc.connector.impl;

import jdbc.connector.JDBCConnector;
import util.ApplicationPropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreJBDCConnectorImpl implements JDBCConnector {
    private final static String PROPERTY_KEY_JBC_POSTGRESQL_URL = "jdbc.postgresql.url";
    private final static String PROPERTY_KEY_JBC_POSTGRESQL_USER = "jdbc.postgresql.user";
    private final static String PROPERTY_KEY_JBC_POSTGRESQL_PASSWORD = "jdbc.postgresql.password";


    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(
                ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JBC_POSTGRESQL_URL),
                ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JBC_POSTGRESQL_USER),
                ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JBC_POSTGRESQL_PASSWORD)
        );
    }
}
