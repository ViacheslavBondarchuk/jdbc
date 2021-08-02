package jdbc.connector;

import java.sql.Connection;
import java.sql.SQLException;

public interface JDBCConnector {
    Connection connect() throws SQLException;
}
