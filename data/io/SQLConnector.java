package data.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.objects.Highscore;
import audio.Song;
import audio.SongInstance;

public class SQLConnector
{

    private Connection myConn = null;
    
    public SQLConnector()
    {
        this("178.62.254.153", "3306","colorstrike","csadmin","aardbei123");
    }

    //Start connection with Databse
    public SQLConnector(String host, String port, String dbName, String userName, String password)
    {
        try
        {
            String url = "jdbc:mysql://" + host + ":" + port + "/"+ dbName + "?user="
            + userName
            + "&password="
            + password;
            Class.forName("com.mysql.jdbc.Driver").newInstance ();
            myConn = DriverManager.getConnection(url);
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
        }
    }
    
    public List<Highscore> getHighscore(Song s, SongInstance si)
    {
    	Statement st = executeResultQuery("SELECT * FROM highscore WHERE songinstance = (SELECT id FROM songinstance WHERE difficulty=" + si.getDifficulty() + " AND song=(SELECT id FROM song WHERE folder='" + s.getFolder() + "')) ");
    	ResultSet result = null;
    	List<Highscore> hsc = new ArrayList<Highscore>();
		try {
			result = st.getResultSet();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
			while(result.next())
			{
				hsc.add(new Highscore(si, result.getString("username"), result.getInt("score"), result.getDate("date").getTime()));
			}
		} catch (SQLException e) {
		}
    	
    	return hsc;
    }
    
    public void update(List<Song> songs)
    {
    	for(Song s : songs)
    	{
    		executeInsertQuery("INSERT INTO song (folder, title, author) VALUES ('" + s.getFolder() + "', '" + s.getTitle() + "', '" + s.getAuthor() + "');");
    		
    		Statement st = executeResultQuery("SELECT id FROM song WHERE folder='" + s.getFolder() + "'");
    		
    		ResultSet rs = null;
    		int id = -1;
    		
			try {
				rs = st.getResultSet();
				rs.next();
				id = rs.getInt(1);
				System.out.println("Song " + id + " added to database");
				rs.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	
    		if(id != -1)
    		{
    			for(SongInstance si : s.getSongs())
    			{
	    			Statement st2 = executeResultQuery("SELECT id FROM songinstance WHERE song=" + id + " AND difficulty='" + si.getDifficulty() + "'");
	        		
	        		ResultSet rs2 = null;
	        		
	    			try {
	    				rs2 = st2.getResultSet();
	    				if(rs2.first())
	    				{
	    					System.err.println("SongInstance Already Exists ");
	    				}
	    				else
	    				{
	    					executeInsertQuery("INSERT INTO songinstance (song, enemies, difficulty) VALUES (" + id + ", " + si.getObjects().size() + ", '" + si.getDifficulty() + "');");
	    					System.out.println("Songinstance Added");
	    				}
	    				rs.close();
	    				st.close();
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
    			}
    		}
    		else
    			System.err.println("Insert Failed");
 
    	}
    }
    
    private void executeInsertQuery(String query)
    {
        try{
            Statement s = myConn.createStatement();
            s.execute(query);
            s.close();
        }
        catch( SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
           System.out.println("getMeasurement: " + ex.getMessage());
        }
    }

    //Execute a custom query
    private Statement executeResultQuery(String query)
    {   
        Statement s = null;
        
        try{
            s = myConn.createStatement();
            s.executeQuery(query);
            
        }
        catch( SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
           System.out.println("getMeasurement: " + ex.getMessage());
        }
        
        return s;
    }
    
    //Close connection with Database
    @Override
    public void finalize() throws Throwable
    {
        // Close database connection
        if( myConn != null )
        {
            try
            {
                myConn.close();
                System.out.println("Database connection terminated");
            }
            catch( Exception e ) {}
        }

        super.finalize();
    }

}