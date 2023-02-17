import java.sql.*;
// Handles database quearying and photon simulation 
//final in order to prevent extensions of the class
public final class Data
{
    Player teamGreen[] = new Player[20];
    Player teamRed[] = new Player[20];
    static Connection con;
    static Statement st;
    //initializes the data class to the database. creates connection and statement class variables which will be used in all other methods
    static void initializeData(String url, String username, String password)
    {
        try{
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
        }catch(Exception SQLException){
            System.out.println("SQL");
        }   
    }
    //inserts a player with the passed in values
    static void insertPlayer(int id, String codeName)
    {
        try{
            st.executeUpdate("insert into \"Players\" (id, codename) VALUES (" + id + ",'" + codeName + "')");
        }catch(Exception SQLException){
            System.out.println("SQL");
        }
    }
}