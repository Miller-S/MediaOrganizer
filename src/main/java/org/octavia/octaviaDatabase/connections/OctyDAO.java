package org.octavia.octaviaDatabase.connections;

import org.octavia.octaviaDatabase.OctyDBPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OctyDAO {
    protected Connection db;
    protected String table;

    public OctyDAO(String table) {
        this.table = table;
    }

    protected void open(){
        try {
            db = OctyDBPool.getInstance().getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    protected StringBuilder createStarSelectQuery(){
        return new StringBuilder("SELECT * FROM " + table);
    }

    protected StringBuilder updateStarter(){
        StringBuilder query = new StringBuilder("UPDATE " + table + " SET ");
        return query;
    }

    protected StringBuilder insertStarter(){
        StringBuilder query = new StringBuilder("INSERT INTO " + table + " (");
        return query;
    }

    protected StringBuilder deleteStarter(){
        StringBuilder query = new StringBuilder("DELETE FROM " + table + " WHERE ");
        return query;
    }

    protected void close(){
        try {
            db.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        db.close();
    }
}
