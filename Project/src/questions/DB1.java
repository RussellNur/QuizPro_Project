package questions;

import java.sql.*;

public class DB1
{
  public static void main(String[] args)
  {
    try
    {
  Connection conn = DriverManager.getConnection("jdbc:derby:bookdb",
                                                     "student622",
                                                     "nopasswd");
       System.out.println("Database now connected.");
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery("select * from authors");

       while (rs.next())
       {
         System.out.println("next record read");
       }
  
       conn.close(); 
       System.out.println("Database now closed."); 
    }

    catch(SQLException sqlex) 
    {
      sqlex.printStackTrace();
    }
  }
}
