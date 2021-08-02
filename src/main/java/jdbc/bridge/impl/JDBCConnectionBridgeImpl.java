package jdbc.bridge.impl;

import jdbc.bridge.JDBCConnectionBridge;
import jdbc.connection.ConfigurableConnection;
import jdbc.connection.impl.PropertyConfigurableConnection;
import jdbc.connector.JDBCConnector;

import java.sql.SQLException;

public class JDBCConnectionBridgeImpl extends JDBCConnectionBridge {
    public JDBCConnectionBridgeImpl(JDBCConnector jdbcConnector) {
        super(jdbcConnector);
    }

    @Override
    public ConfigurableConnection getConnection() throws SQLException {
        return new PropertyConfigurableConnection(jdbcConnector.connect());
    }
}
