package jdbc.factory.impl;

import jdbc.connection.ConfigurableConnection;
import jdbc.factory.AbstractJDBCPooledConnectionFactory;

import java.sql.SQLException;

public class JDBCPooledConnectionFactoryImpl extends AbstractJDBCPooledConnectionFactory {
    private static JDBCPooledConnectionFactoryImpl instance;

    public static JDBCPooledConnectionFactoryImpl getInstance() {
        if (instance == null) {
            synchronized (JDBCPooledConnectionFactoryImpl.class) {
                if (instance == null) {
                    instance = new JDBCPooledConnectionFactoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public ConfigurableConnection getConnection() throws SQLException, InterruptedException {
        ConfigurableConnection configurableConnection = null;
        synchronized (JDBCPooledConnectionFactoryImpl.this.lock) {
            while (usedConnections.size() == CONNECTION_POOL_SIZE) {
                this.lock.wait(1000L);
            }

            if (connectionPool.size() > 0) {
                configurableConnection = connectionPool.remove(connectionPool.size() - 1);
            } else {
                configurableConnection = jdbcConnectionBridge.getConnection();
            }
            usedConnections.add(configurableConnection);
        }
        System.out.println("used connection: " + usedConnections.size());
        return configurableConnection;
    }

    @Override
    public boolean releaseConnection(ConfigurableConnection configurableConnection) throws SQLException {
        usedConnections.remove(configurableConnection);
        if (configurableConnection != null && !configurableConnection.isClosed()) {
            connectionPool.add(configurableConnection);
        }
        return true;
    }


}
