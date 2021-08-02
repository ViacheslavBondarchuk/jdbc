package jdbc.factory;

import jdbc.bridge.JDBCConnectionBridge;
import jdbc.bridge.impl.JDBCConnectionBridgeImpl;
import jdbc.connection.ConfigurableConnection;
import jdbc.connector.impl.PostgreJBDCConnectorImpl;
import util.ApplicationPropertiesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractJDBCPooledConnectionFactory implements JDBCPooledConnectionFactory {
    protected final static int CONNECTION_POOL_SIZE = Integer.valueOf(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_CONNECTION_POOL_SIZE));

    protected final Thread jdbcPooledConnectionFactoryShutDownListener;
    protected final List<ConfigurableConnection> usedConnections;
    protected final List<ConfigurableConnection> connectionPool;
    protected final JDBCConnectionBridge jdbcConnectionBridge;
    protected final Object lock;

    public AbstractJDBCPooledConnectionFactory() {
        this.connectionPool = Collections.synchronizedList(new ArrayList<>(CONNECTION_POOL_SIZE));
        this.jdbcConnectionBridge = new JDBCConnectionBridgeImpl(new PostgreJBDCConnectorImpl());
        this.usedConnections = Collections.synchronizedList(new ArrayList<>(0));
        this.jdbcPooledConnectionFactoryShutDownListener = new Thread(this::shutdown);
        this.lock = new Object();
    }

    @Override
    public int getConnectionPoolSize() {
        return CONNECTION_POOL_SIZE;
    }

    @Override
    public int getUsedConnectionSize() {
        return usedConnections.size();
    }

    @Override
    public int getAllowedConnectionPoolSize() {
        return connectionPool.size();
    }

    @Override
    public void registerShutDownListener() {
        Runtime.getRuntime().addShutdownHook(jdbcPooledConnectionFactoryShutDownListener);
    }

    public void shutdown() {
        try {
            for (ConfigurableConnection configurableConnection : connectionPool) {
                if (configurableConnection != null) {
                    configurableConnection.close();
                }
            }

            for (ConfigurableConnection configurableConnection : usedConnections) {
                if (configurableConnection != null) {
                    configurableConnection.close();
                }
            }

            connectionPool.clear();
            usedConnections.clear();
        } catch (IOException e) {
        }
    }
}
