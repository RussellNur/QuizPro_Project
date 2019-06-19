package questions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class DatabaseAssignment {

    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    
    public static void main(String[] args)
    {
        new DatabaseAssignment().go(args);
        System.out.println("SimpleApp finished");
    }
    
    void go(String[] args)
    {
        /* parse the arguments to determine which framework is desired*/
    	try {

    		
    		

    	
        parseArguments(args);
        
        System.out.println("Database Assignment starting in " + framework + " mode");
        
        Connection conn = null;
        ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
        PreparedStatement psInsert;
        PreparedStatement psUpdate;
        Statement s;
        ResultSet rs = null;
        try
        {
            String dbName = "QuizTakers"; // the name of the database
        	int runs = 0;
        	while (true) {
        		runs++;
//        		if (runs == 1) {
//        			if (framework.equals("embedded"))
//                    {
//
//                            DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
//                        
//                    }
//        		}
        		
            	String[] quizName = (questions.Main.greeting()).split(" ");
            	
            	String quiz = quizName[0];
            	String name = quizName[1];
            	String programmer = quizName[2];
            	
//            	System.out.println(quiz.substring(0, quiz.indexOf('.')));
//            	System.out.println(name);
            	if (quiz.equals("exit") || name.equals("exit")) {
            		
            		System.out.println("Thank you for using QuizPro!");
                    if (framework.equals("embedded"))
                    {
                        try
                        {
//                          s.execute("drop table quizTakers");
//                          System.out.println("Dropped table quizTakers");
                        	
                        	
                        	if (runs >= 1) {
                                conn = DriverManager.getConnection(protocol + dbName
                                        + ";create=true");
                               conn.setAutoCommit(false);
                               s = conn.createStatement();
                               statements.add(s);
                              s.execute("drop table quiztakers");
                              s.execute("drop table quizzes");
                              conn.commit();
                              System.out.println("Dropped table quiztakers and table quizzes");
                        	}
                        
                            // the shutdown=true attribute shuts down Derby
                            //DriverManager.getConnection("jdbc:derby:;shutdown=true");

                            // To shut down a specific database only, but keep the
                            // engine running (for example for connecting to other
                            // databases), specify a database in the connection URL:
                            DriverManager.getConnection("jdbc:derby:;shutdown=true");
                        }
                        catch (SQLException se)
                        {
                            if (( (se.getErrorCode() == 50000)
                                    && ("XJ015".equals(se.getSQLState()) ))) {
                                // we got the expected exception
                                System.out.println("Derby shut down normally");
                                // Note that for single database shutdown, the expected
                                // SQL state is "08006", and the error code is 45000.
                                break;
                            }
                            else {
                                // if the error code or SQLState is different, we have
                                // an unexpected exception (shutdown failed)
                                System.err.println("Derby did not shut down normally");
                                printSQLException(se);
                                break;
                            }
                        }
                    }
            		
            	}
            	else if (quiz.equals("add") || name.equals("add")) {
            		continue;
            	}
            	

            	
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user");
            props.put("password", "user");
            
            conn = DriverManager.getConnection(protocol + dbName + ";create=true");
            System.out.println("Connected to and/or created database " + dbName);
            
            conn.setAutoCommit(false);
            s = conn.createStatement();
            statements.add(s);
            if (runs == 1) {
            	s.execute("create table quiztakers(name varchar(40), quiz varchar(40), score double, programmer int, primary key(name, quiz))");
                System.out.println("Created table quiztakers");
                s.execute("create table quizzes(quiz varchar(40))");
                System.out.println("Created table quizzes");
            }
            
//            DatabaseMetaData dbmd = conn.getMetaData();
//            
//            ResultSet rsmd = dbmd.getTables(null, null, null, new String[] {"TABLE"});
//                 
//            System.out.println("Here are all the table names: ");
//            while (rsmd.next()) 
//               System.out.println(rsmd.getString("TABLE_NAME"));
            if (runs == 1) {
            Scanner infile = new Scanner(new File("quizzes.txt")); // Open the file
            File quizzesFile = new File("quizzes.txt");
            	
            	psInsert = conn.prepareStatement(
                        "insert into quizzes values (?)");
    	        statements.add(psInsert);
    	        while (infile.hasNext()) {
    	        psInsert.setString(1, infile.nextLine()+".dat");
    	     
    	        psInsert.executeUpdate();
    	        }
    	        infile.close();
    	        
            System.out.println("Populated with quizzes table quizzes");
            }
            
            if (quiz.equals("lb")) {
                rs = s.executeQuery(
                        "select * from quiztakers order by score desc");
                System.out.println("Leaderboard table in Descending order:");
                while (rs.next())
                {
                  System.out.println(rs.getString(1) + " " + 
                          rs.getString(2) + " " +
                          rs.getString(3) + " " + rs.getString(4));            
                }
            	continue;
            }
            if (quiz.equals("lbjava")) {
                rs = s.executeQuery(
                        "select name, score, quizzes.quiz from quiztakers, quizzes where score > 0 and quizzes.quiz = quiztakers.quiz order by score desc");
                System.out.println("Leaderboard Java table (for score > 0):");
                while (rs.next())
                {
                  System.out.println(rs.getString(1) + " " + 
                          rs.getString(2) + " " + rs.getString(3));            
                }
            	continue;
            }

            
        	double quizScore = questions.Main.startAndGradeTheQuiz(quiz); 
        	System.out.println(quizScore);
        	
            
//            if (tableExist(conn, "quiztakers")) {
//            	System.out.println("Table quiztakers already exists");
//            }
//            else if ((tableExist(conn, "quiztakers")==false) && (runs >= 1)){
//            	s.execute("create table quiztakers(name varchar(40), quiz varchar(40), score double)");
//                System.out.println("Created table quiztakers");
//            }
            	
//            DatabaseMetaData dbm = conn.getMetaData();
//         // check if "employee" table is there
//         ResultSet tables = dbm.getTables(null, null, "quiztakers", null);
//         if (tables.next()) {
//           System.out.println("QUIZTAKERS already exists");
//         }
//         else {
//        	 s.execute("create table quiztakers(name varchar(40), quiz varchar(40), score double)");
//             System.out.println("Created table quiztakers");
//         }
            
            
//            if (runs == 1) {
//            s.execute("create table quizTakers(name varchar(40), quiz varchar(40), score double)");
//            System.out.println("Created table quizTakers");
//            }

            psInsert = conn.prepareStatement(
                    "insert into quiztakers values (?, ?, ?, ?)");
	        statements.add(psInsert);
	
	        psInsert.setString(1, name);
	        psInsert.setString(2, quiz);
	        psInsert.setDouble(3, quizScore);
	        psInsert.setString(4, programmer);
	        psInsert.executeUpdate();
	        System.out.println("Inserted " + name + " " + quiz + " " +  quizScore + " " + programmer);
            

            // Let's update some rows as well...

//            psUpdate = conn.prepareStatement(
//                        "update quizTakers set score=? where name=?");
//            statements.add(psUpdate);
//
//            psUpdate.setDouble(1, 2.0);
//            psUpdate.setString(2, name);
//            psUpdate.executeUpdate();
//            System.out.println("Updated " + quizScore + " to 2");
            System.out.println("Quiz Takers:");
            rs = s.executeQuery(
                    "select * from quiztakers");
            while (rs.next())
            {
              System.out.println(rs.getString(1) + " " + 
                      rs.getString(2) + " " +
                      rs.getString(3) + " " + rs.getString(4));            
            }
            System.out.println("Quizzes:");
            rs = s.executeQuery(
                    "select * from quizzes");
            while (rs.next())
            {
              System.out.println(rs.getString(1));            
            }
           // statements.add
            
            double score; // user score retrieved from the database
            boolean failure = false;


//            if ((score = rs.getInt(1)) != 300)
//            {
//                failure = true;
//                reportFailure(
//                        "Wrong row returned, expected num=300, got " + score);
//            }

//            if (!rs.next())
//            {
//                failure = true;
//                reportFailure("Too few rows");
//            }

//            if ((score = rs.getDouble(1)) != 3.0)
//            {
//                failure = true;
//                reportFailure(
//                        "Wrong row returned, expected score=3, got " + score);
//            }

            if (rs.next())
            {
                failure = true;
                reportFailure("Too many rows");
            }

            if (!failure) {
                System.out.println("Verified the rows");
            }

            // delete the table
//            s.execute("drop table quizTakers");
//            System.out.println("Dropped table quizTakers");

            /*
               We commit the transaction. Any changes will be persisted to
               the database now.
             */
            conn.commit();
            System.out.println("Committed the transaction");
        }
        	
        	
        }
        catch (SQLException sqle)
        {
            printSQLException(sqle);
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }

            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement)statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    printSQLException(sqle);
                }
            }

            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
            
        }
    	}
		catch (EmptyFileException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
			ex.printStackTrace();
		}
    	catch (FileNotFoundException ex)
        {
            System.out.println("FileNotFoundException"); 
            ex.printStackTrace();   
        }
		catch (IOException e1) {
			System.out.println("There was a mistake. Try to run the program again.");
			e1.printStackTrace();
		} 
    }

        
        
    
    
    private void reportFailure(String message) {
        System.err.println("\nData verification failed:");
        System.err.println('\t' + message);
    }

    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
    
    private void parseArguments(String[] args)
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient"))
            {
                framework = "derbyclient";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }

    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) { 
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
}
