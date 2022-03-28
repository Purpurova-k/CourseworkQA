package ru.netology.database;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {

    private DbHelper() {
    }

    private final static Connection conn = getConnection();
    private final static QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
    }

    @SneakyThrows
    public static void cleanAllTables() {
        runner.execute(conn, "DELETE FROM app.order_entity;");
        runner.execute(conn, "DELETE FROM app.credit_request_entity;");
        runner.execute(conn, "DELETE FROM app.payment_entity;");
    }

    @SneakyThrows
    public static CreditRequestEntityInfo getInfoFromCreditRequestTable() {
        var creditRequestSQL = "SELECT * FROM credit_request_entity WHERE created = (SELECT MAX(created) FROM credit_request_entity);";
        return runner.query(conn, creditRequestSQL, new BeanHandler<>(CreditRequestEntityInfo.class));
    }

    @SneakyThrows
    public static OrderEntityInfo getInfoFromOrderTable() {
        var orderSQL = "SELECT * FROM order_entity WHERE created = (SELECT MAX(created) FROM order_entity);";
        return runner.query(conn, orderSQL, new BeanHandler<>(OrderEntityInfo.class));
    }

    @SneakyThrows
    public static PaymentEntityInfo getInfoFromPaymentTable() {
        var orderSQL = "SELECT * FROM payment_entity WHERE created = (SELECT MAX(created) FROM payment_entity);";
        return runner.query(conn, orderSQL, new BeanHandler<>(PaymentEntityInfo.class));
    }
}
