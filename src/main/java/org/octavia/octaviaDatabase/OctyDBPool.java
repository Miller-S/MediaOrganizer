package org.octavia.octaviaDatabase;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class OctyDBPool {

    private static ComboPooledDataSource pool = new ComboPooledDataSource();
    //private String url = "jdbc:sqlserver://localhost:1433;databaseName=OctyBooru;integratedSecurity=true";
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=OctyBooru";
    private String username = "Tester";
    private String password = "password";
    private static OctyDBPool octyPool;
    String changePath =  "changelog.xml";

    private OctyDBPool() {
        //pool.setDriverClass("org.h2.Driver");
        pool.setJdbcUrl(url);
        pool.setUser(username);
        pool.setPassword(password);
        pool.setMinPoolSize(5);
        pool.setAcquireIncrement(5);
        pool.setMaxPoolSize(20);
        updateServer();
    }

    public Liquibase getLiquibase(Connection connection) throws DatabaseException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        return new liquibase.Liquibase(changePath, new ClassLoaderResourceAccessor(), database);
    }

    public void updateServer(){
        try {
            Connection connection = getConnection();
            Liquibase liquibase = getLiquibase(connection);
            if(liquibase.isSafeToRunUpdate())
                liquibase.update(new Contexts(), new LabelExpression());
            connection.close();
        } catch (SQLException | LiquibaseException throwables) {
            throwables.printStackTrace();
        }
    }

    public void rollback(int changes){
        try {
            Connection connection = getConnection();
            Liquibase liquibase = getLiquibase(connection);
            liquibase.rollback(changes, new Contexts(), new LabelExpression());
            connection.close();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }

    public static OctyDBPool getInstance(){
        if(octyPool == null)
            octyPool = new OctyDBPool();
        return octyPool;
    }

    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

}
