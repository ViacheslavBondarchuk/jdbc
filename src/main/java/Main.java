import jdbc.connection.ConfigurableConnection;
import jdbc.factory.JDBCPooledConnectionFactory;
import jdbc.factory.impl.JDBCPooledConnectionFactoryImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        JDBCPooledConnectionFactory jdbcPooledConnectionFactory = JDBCPooledConnectionFactoryImpl.getInstance();
        new Thread(() -> {
            try {
                jdbcPooledConnectionFactory.getConnection();
                jdbcPooledConnectionFactory.getConnection();
                jdbcPooledConnectionFactory.getConnection();
                final ConfigurableConnection connection = jdbcPooledConnectionFactory.getConnection();
                System.out.println("Before sleep");
                Thread.sleep(5000);
                jdbcPooledConnectionFactory.releaseConnection(connection);
            } catch (SQLException | InterruptedException e) {}

        }).start();

       new Thread(() -> {
           try {
               System.out.println("Before get connection1");
               final ConfigurableConnection connection = jdbcPooledConnectionFactory.getConnection();
               jdbcPooledConnectionFactory.releaseConnection(connection);
               Thread.sleep(5000);

               System.out.println("connection is gotten1");
           } catch (SQLException | InterruptedException e) {}
       }).start();

       new Thread(() -> {
           try {
               System.out.println("Before get connection2");
               final ConfigurableConnection connection = jdbcPooledConnectionFactory.getConnection();
               jdbcPooledConnectionFactory.releaseConnection(connection);
               Thread.sleep(5000);

               System.out.println("connection is gotten2");
           } catch (SQLException | InterruptedException e) {}
       }).start();

    }
}
