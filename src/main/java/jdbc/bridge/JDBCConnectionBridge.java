package jdbc.bridge;

import jdbc.connection.ConfigurableConnection;
import jdbc.connector.JDBCConnector;

import java.sql.SQLException;

public abstract class JDBCConnectionBridge {
    protected JDBCConnector jdbcConnector;

    public JDBCConnectionBridge(JDBCConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    abstract public ConfigurableConnection getConnection() throws SQLException;
}
