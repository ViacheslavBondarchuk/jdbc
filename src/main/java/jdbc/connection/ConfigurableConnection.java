package jdbc.connection;

import util.ApplicationPropertiesUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class ConfigurableConnection implements Closeable {
    protected final static String PROPERTY_KEY_JDBC_AUTOCOMMIT = "jdbc.autocommit";
    protected final static String PROPERTY_KEY_JDBC_READ_ONLY = "jdbc.readOnly";
    protected final static String PROPERTY_KEY_JDBC_NETWORK_TIMEOUT = "jdbc.network.timeout";
    protected final static String PROPERTY_KEY_JDBC_NETWORK_TIMEOUT_THREADS = "jdbc.network.timeout.threads";
    protected final static String PROPERTY_KEY_JDBC_TRANSACTION_ISOLATION_LEVEL = "jdbc.transaction.isolationLevel";
    protected final static String PROPERTY_KEY_JDBC_CATALOG = "jdbc.catalog";
    protected final static String PROPERTY_KEY_JDBC_SCHEME = "jdbc.scheme";

    protected final Connection connection;
    protected final ExecutorService executorService;

    public ConfigurableConnection(Connection connection) {
        this.connection = connection;
        this.executorService = Executors.newFixedThreadPool(Integer.valueOf(ApplicationPropertiesUtil.getProperty(PROPERTY_KEY_JDBC_NETWORK_TIMEOUT_THREADS)), Executors.defaultThreadFactory());
        this.init();
    }

    private void init() {
        try {
            this.setAutoCommit();
            this.setReadOnly();
            this.setNetworkTimeout();
            this.setCatalog();
            this.setScheme();
            this.setTransactionIsolationLevel();
        } catch (SQLException e) {
        }
    }

    protected abstract void setAutoCommit() throws SQLException;

    protected abstract void setReadOnly() throws SQLException;

    protected abstract void setNetworkTimeout() throws SQLException;

    protected abstract void setCatalog() throws SQLException;

    protected abstract void setScheme() throws SQLException;

    protected abstract void setTransactionIsolationLevel() throws SQLException;

    public boolean isClosed() throws SQLException {
        return this.connection.isClosed();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.connection.prepareStatement(sql);
    }

    public CallableStatement getCallableStatement(String sql) throws SQLException {
        return this.connection.prepareCall(sql);
    }

    public boolean getAutocommit() throws SQLException {
        return this.connection.getAutoCommit();
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
    }

    @Override
    public void close() throws IOException {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
        }
    }
}
