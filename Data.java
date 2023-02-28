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
            System.out.println("ERROR : COULD NOT ESTABLISH CONNECTION TO DATABASE");
        }   
    }
    //inserts a player with the passed in values
    static void insertPlayer(int id, String codeName)
    {
        try{
            st.executeUpdate("insert into \"Players\" (id, codename) VALUES (" + id + ",'" + codeName + "')");
        }catch(Exception SQLException){
            System.out.println("ERROR : ID ALREADY UTILIZED");
        }
    }
    //checks to see if an id is present in the database
    static boolean checkForID(int id)
    {
        try{
            //saves id column to a Result Set
            ResultSet rs =  st.executeQuery("SELECT \"id\" from \"Players\" WHERE \"id\" = " + id);
            //if there is a value in the result set, return true, else, return false
            if(rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }catch(Exception SQLException){
            System.out.println("ERROR : COULD NOT ESTABLISH CONNECTION TO DATABASE");
            return false;
        }
    }
    //returns player codename given an id
    //pleaaaaase don't call this function without first checking for id
    static String getCodeName(int id)
    {
        try{
            //creates ResultSet of all codenames where ID
            ResultSet rs = st.executeQuery("SELECT \"codename\" from \"Players\" WHERE \"id\" = " + id);
            if(rs.next())
            {
                //returns codename 
                return rs.getString("codename");
            }
            else
            {
                //returns that the id could not be found
                return "COULD NOT FIND ID";
            }
        }catch(Exception SQLException){
            //returns that the ID could not be found, 
            System.out.println("ERROR : COULD NOT ESTABLISH CONNECTION TO DATABASE");
            return "COULD NOT FIND ID";
        }
    }

}