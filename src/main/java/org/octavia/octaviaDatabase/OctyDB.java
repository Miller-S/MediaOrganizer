package org.octavia.octaviaDatabase;

import org.octavia.octaviaDatabase.dataTypes.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton that handles connections to the sql database.
 */
public class OctyDB {

    //the singleton instance of this class
    private static OctyDB db;
    //the database url
    private static String url;
    //the connection to the database
    private static Connection connect;
    //a hashmap linking the query names to the prepared statement for that query
    private static HashMap<queries , PreparedStatement> prepped = new HashMap<>();

    /**
     * Enum containing all the queries that are prepared when database class has been created
     */
    public enum queries {
        GET_MEDIA_WITH_TAG_ID, INSERT_TAG, DELETE_TAG, FIND_TAG, GET_ALL_MEDIA, GET_MEDIA_ID_WITH_TAG_ID,
        GET_TAGS_FROM_MEDIA_ID, INSERT_MEDIA, GET_MEDIA_ID_FROM_PATH, COUNT_TOP_TAG_USES, GET_RELATED_TAGS
    }

    /**
     * Create the prepared statements which will most commonly be accessed by the program.
     * Each statement is paired with a enum queries and then stored in a hash map. {@link queries}
     *
     * @throws SQLException
     */
    private void createPreparedStatements() throws SQLException {
        //get all the media that has been tagged with a tag ID
        prepped.put(queries.GET_MEDIA_WITH_TAG_ID, connect.prepareStatement(
                "SELECT Media.MediaID, Path, Tag.TagID, TagName " +
                        "FROM Tag INNER JOIN TagFileLink ON Tag.TagID = TagFileLink.TagID " +
                        "INNER JOIN Media ON (Media.MediaID = TagFileLink.MediaID) " +
                        "WHERE tag.TagID = ?;"));
        //Get all the tags for a piece of media
        prepped.put(queries.GET_TAGS_FROM_MEDIA_ID, connect.prepareStatement(
                "SELECT Tag.TagID, TagName " +
                        "FROM Tag INNER JOIN TagFileLink ON Tag.TagID = TagFileLink.TagID " +
                        "WHERE TagFileLink.MediaID = ?;"));
        //Get all the media ID's that have been tagged with the tag id
        prepped.put(queries.GET_MEDIA_ID_WITH_TAG_ID, connect.prepareStatement(
                "SELECT MediaID, Tag.TagID, TagName " +
                        "FROM Tag INNER JOIN TagFileLink ON Tag.TagID = TagFileLink.TagID " +
                        "WHERE tag.TagID = ?;"));
        //add a new tag
        prepped.put(queries.INSERT_TAG, connect.prepareStatement(
                "INSERT INTO Tag (TagName, Description) VALUES ( ? , ? )"));
        //delete a tag
        prepped.put(queries.DELETE_TAG, connect.prepareStatement(
                "DELETE FROM Tag WHERE TagID = ?"));
        // find a tag from its name
        prepped.put(queries.FIND_TAG, connect.prepareStatement(
                "SELECT * FROM Tag Where TagName = ?"));
        //get all the media in the database.
        prepped.put(queries.GET_ALL_MEDIA, connect.prepareStatement(
                "SELECT * FROM Media"));
        //Get the media id that is located at the path location
        prepped.put(queries.GET_MEDIA_ID_FROM_PATH, connect.prepareStatement(
                "SELECT MediaID FROM Media WHERE Path = ?"));
        //insert a new piece of media
        prepped.put(queries.INSERT_MEDIA, connect.prepareStatement(
                "INSERT INTO Media (Path) VALUES ( ? )"));
        //count how many times each tag is used
        prepped.put(queries.COUNT_TOP_TAG_USES,connect.prepareStatement(
                "SELECT TOP ? Tag.[TagName], Tag.[TagID],COUNT(TagFileLink.TagID) AS 'count'" +
                        " FROM [dbo].[TagFileLink] " +
                        "INNER JOIN Tag ON Tag.TagID = TagFileLink.TagID " +
                        "GROUP BY Tag.TagID, Tag.TagName " +
                        "ORDER BY count desc"
        ));
        //Get the tags related to other tags
        prepped.put(queries.COUNT_TOP_TAG_USES,connect.prepareStatement(
                "SELECT TOP ? Tag.[TagName], Tag.[TagID],COUNT(TagFileLink.TagID) AS 'count'" +
                        " FROM [dbo].[TagFileLink] " +
                        "INNER JOIN Tag ON Tag.TagID = TagFileLink.TagID " +
                        "GROUP BY Tag.TagID, Tag.TagName " +
                        "ORDER BY count desc"
        ));


    }

    /**
     * Private constructor for the singleton. Will set up connection to our desired database as well as creating
     * prepared statements that are commonly used {@link #createPreparedStatements()}
     * @param url a string that contains the url for the database we are connecting to.
     */
    private OctyDB(String url){
        this.url = url;
        try{
            connect = DriverManager.getConnection(url);
            createPreparedStatements();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Constructor with default local host sql database
     */
    private OctyDB(){
        this("jdbc:sqlserver://localhost:1433;databaseName=OctyBooru;integratedSecurity=true");
    }
    /**
     * Gets the prepared statement that is related to the query
     * @param q which prepared statement to get
     * @return the prepared statement
     */
    public PreparedStatement getPreparedStatement(queries q){
        return prepped.get(q);
    }

    /**
     * Returns the tags that are associated with the media id given.
     * @param mediaID 
     * @return tags that were found
     * @throws SQLException
     */
    public ArrayList getTagsFromMediaID(int mediaID) throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();
        PreparedStatement p = prepped.get(queries.GET_TAGS_FROM_MEDIA_ID);
        p.setInt(1,mediaID);
        ResultSet rs = p.executeQuery();
        while (rs.next()){
            tags.add(new Tag(rs.getString("TagName"), rs.getInt("TagID")));
        }
        return tags;
    }

    public ArrayList getTopTags(int top) throws SQLException {
        ArrayList<Tag> tags = new ArrayList<>();
        PreparedStatement p = prepped.get(queries.COUNT_TOP_TAG_USES);
        p.setInt(1,top);
        ResultSet rs = p.executeQuery();
        while (rs.next()){
            tags.add(new Tag(rs.getString("TagName"), rs.getInt("TagID")));
        }
        return tags;
    }

    /**
     * Initiates the singleton or gets the only instance of the database class.
     * @return The database connection class
     */
    public static OctyDB getInstance(){
        if(db == null)
            db = new OctyDB();
        return db;
    }

    /**
     * Creates a statement for the current connection
     * @return Statement
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException{
        return connect.createStatement();
    }

    /**
     * Find if a tag already exists in the database.
     * @param tag name of the tag
     * @return true if tag already exists
     * @throws SQLException
     */
    public boolean tagIsInDatabase(String tag) throws SQLException {
        PreparedStatement search = prepped.get(queries.FIND_TAG);
        search.setString(1, tag);
        ResultSet rs = search.executeQuery();
        return rs.next();

    }

    /**
     * Add a new tag to the database
     * @param tag name of the tag
     * @param description description of the tag
     * @return returns true if tag was not in the database and was added
     * @throws SQLException
     */
    public boolean addTag(String tag, String description) throws SQLException {
        if (tagIsInDatabase(tag))
            return false;
        PreparedStatement insert = prepped.get(queries.INSERT_TAG);
        insert.setString(1, tag);
        if(description == null)
            insert.setNull(2,Types.VARCHAR);
        else
            insert.setString(2, description);
        int rows = insert.executeUpdate();
        return true;
    }

    /**
     * Add a new tag to the database
     * @param tag name of the tag
     * @return returns true if tag was not in the database and was added
     * @throws SQLException
     */
    public boolean addTag(String tag) throws SQLException {
        return addTag(tag,null);
    }

    /**
     * Remove a tag from the database
     * @param tagID the id of the tag to be removed
     * @throws SQLException
     */
    public void removeTagByID(int tagID) throws SQLException {
        int index = 2;
        PreparedStatement del = prepped.get(queries.DELETE_TAG);
        del.setInt(1, tagID);
        int rows = del.executeUpdate();
    }

    /**
     * Find all media that has been tagged with the given id
     * @param tagID the tag id to search on
     * @return the result set of all the tags.
     * @throws SQLException
     */
    public ResultSet getMediasWithTagID(int tagID) throws SQLException {
        PreparedStatement search = prepped.get(queries.GET_MEDIA_ID_WITH_TAG_ID);
        search.setInt(1,tagID);
        ResultSet rs = search.executeQuery();
        search.clearParameters();
        return rs;
    }
}
