import java.sql.*;
// Handles database quearying and photon simulation 
class Data
{
    Player teamGreen[] = new Player[20];
    Player teamRed[] = new Player[20];

    Data()    
    {
    }
    void initializeData(String url, String username, String password)
    {
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            int count = st.executeUpdate("insert into \"Test\" (id) VALUES (15)");
            System.out.println(count);
        }catch(Exception SQLException){
            System.out.println("SQL");
        }
        
    }
}