package jdbc.factory;

import jdbc.connection.ConfigurableConnection;

import java.sql.SQLException;

public interface JDBCPooledConnectionFactory {
    String PROPERTY_KEY_JDBC_CONNECTION_POOL_SIZE = "jdbc.connection.pool.size";

    ConfigurableConnection getConnection() throws SQLException, InterruptedException;

    boolean releaseConnection(ConfigurableConnection configurableConnection) throws SQLException;

    int getConnectionPoolSize();

    int getUsedConnectionSize();

    int getAllowedConnectionPoolSize();

    void registerShutDownListener();

    void shutdown();

}
