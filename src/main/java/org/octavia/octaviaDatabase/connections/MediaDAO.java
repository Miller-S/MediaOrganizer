package org.octavia.octaviaDatabase.connections;

import org.octavia.octaviaDatabase.dataTypes.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MediaDAO extends OctyDAO{

    public MediaDAO() {
        super("Media");
    }

    public Media getMediaFromID(int id) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = createStarSelectQuery();
        query.append(" WHERE MediaID = " + id);
        ResultSet rs = state.executeQuery(query.toString());
        Media media = null;
        if(rs.next()){
            media = new Media(rs.getInt("MediaID"),
                    rs.getString("Path"));
        }
        rs.close();
        close();
        return media;

    }

    public Media getMediaFromPath(String path) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = createStarSelectQuery();
        query.append(" WHERE Path = '" + path +"'");
        ResultSet rs = state.executeQuery(query.toString());
        Media media = null;
        if(rs.next()){
            media = new Media(rs.getInt("MediaID"),
                    rs.getString("Path"));
        }
        rs.close();
        close();
        return media;

    }

    public boolean updateMedia(Media update) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = updateStarter();
        query.append("Path = '" + update.getPath() + "'");
        query.append(" WHERE MediaID = " + update.getMediaID());
        int count = state.executeUpdate(query.toString());
        state.close();
        close();
        return count > 0;
    }

    public boolean insertMedia(Media media) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = insertStarter();
        query.append("Path) ");
        query.append("VALUES ('" + media.getPath() + "')");
        //System.out.println(query.toString());
        int count = state.executeUpdate(query.toString());
        state.close();
        close();
        return count > 0;

    }

    public boolean deleteMedia(int id) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = deleteStarter();
        query.append("MediaID = " + id);
        int count = state.executeUpdate(query.toString());
        state.close();
        close();
        return count > 0;

    }
}
