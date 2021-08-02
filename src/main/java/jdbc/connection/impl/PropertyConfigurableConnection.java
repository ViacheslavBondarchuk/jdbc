package jdbc.connection.impl;

import jdbc.connection.ConfigurableConnection;
import util.ApplicationPropertiesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class PropertyConfigurableConnection extends ConfigurableConnection {

    public PropertyConfigurableConnection(Connection connection) {
        super(connection);
    }

    @Override
    protected void setAutoCommit() throws SQLException {
        connection.setAutoCommit(Boolean.valueOf(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_AUTOCOMMIT)));
    }

    @Override
    protected void setReadOnly() throws SQLException {
        connection.setReadOnly(Boolean.valueOf(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_READ_ONLY)));
    }

    @Override
    protected void setNetworkTimeout() throws SQLException {
        connection.setNetworkTimeout(executorService, (int)TimeUnit.SECONDS.toMillis(Long.valueOf(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_NETWORK_TIMEOUT))));
    }


    @Override
    protected void setCatalog() throws SQLException {
        connection.setCatalog(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_CATALOG));
    }

    @Override
    protected void setScheme() throws SQLException {
        connection.setSchema(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_SCHEME));
    }

    @Override
    protected void setTransactionIsolationLevel() throws SQLException {
        connection.setTransactionIsolation(Integer.valueOf(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_TRANSACTION_ISOLATION_LEVEL)));
    }
}
