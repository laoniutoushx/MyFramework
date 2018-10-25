package sos.haruhi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName DaoOperator
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/25 21:20
 * @Version 10032
 **/
public class DaoOperator {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC", "root", "2012");
        return connection;
    }

    public void insert(Connection connection, List<Bean> beanList) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("insert into exam values(?, ?, ?, ?)");
        for(Bean bean:beanList){
            statement.setObject(1, bean.getId());
            statement.setObject(2, bean.getName());
            statement.setObject(3, bean.getAge());
            statement.setObject(4, bean.getAddress());
            statement.addBatch();
        }
        statement.executeBatch();
        connection.commit();

        statement.close();
        connection.close();
    }
}
