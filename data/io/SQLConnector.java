package data.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.crypto.provider.RSACipher;

import model.objects.highscore.Highscore;
import audio.Song;
import audio.SongInstance;

public class SQLConnector
{
    private Connection myConn = null;
    public 	SQLConnector()
    {
        this("178.62.254.153", "3306","colorstrike","csadmin","aardbei123");
    }

    //Start connection with Database
    public SQLConnector(String host, String port, String dbName, String userName, String password)
    {
        try  {
            String url = "jdbc:mysql://" + host + ":" + port + "/"+ dbName + "?user="
            + userName
            + "&password="
            + password;
            Class.forName("com.mysql.jdbc.Driver").newInstance ();
            myConn = DriverManager.getConnection(url);
        }
        catch( SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
        }
    }
    
    public List<Highscore> getHighscores(Song s, SongInstance si)
    {
    	String query = "SELECT * FROM highscore WHERE songinstance = (SELECT id FROM songinstance WHERE difficulty='" + si.getDifficulty() + "' AND song=(SELECT id FROM song WHERE folder='" + s.getFolder() + "')) ORDER BY score DESC";
    	//System.out.println(query);
    	Statement st = executeResultQuery(query);
    	ResultSet result = null;
    	List<Highscore> hsc = new ArrayList<>();

		try {
			result = st.getResultSet();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
			while(result.next())
				hsc.add(new Highscore(si, result.getString("username"), result.getInt("score"), result.getDate("date").getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return hsc;
    }
    
    public List<Highscore> getHighscoresToday(Song s, SongInstance si)
    {
    	Statement st = executeResultQuery("SELECT * FROM highscore WHERE date=CURDATE() AND songinstance = (SELECT id FROM songinstance WHERE difficulty='" + si.getDifficulty() + "' AND song=(SELECT id FROM song WHERE folder='" + s.getFolder() + "')) ORDER BY score DESC");
    	ResultSet result = null;
    	List<Highscore> hsc = new ArrayList<>();
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
			e.printStackTrace();
		}
    	
    	return hsc;
    }
    
    public int addHighscore(Song s, SongInstance si, String name, int currentScore) {
    	if(name.length() <= 5 && currentScore <= Integer.MAX_VALUE)	{
			String query = "INSERT INTO highscore (username, score, date, songinstance) VALUES ('" + name.trim() + "', " + currentScore + ", CURDATE(), (SELECT id FROM songinstance WHERE difficulty='" + si.getDifficulty() + "' AND song=(SELECT id FROM song WHERE folder='" + s.getFolder() + "')))";
			//System.out.println(query);
			return executeInsertQuery(query);
		}
    	return -1;		
	}
    
    public void addPlaydata(Song s, SongInstance si, long time, int enemies_missed, int enemies_hit, int buttons_pressed, int  joystick_moved) {
    	System.out.println("Inserting PlayData: " + time + ", " + enemies_missed + ", " + enemies_hit + ", " + buttons_pressed + ", " + joystick_moved);
		String query = "INSERT INTO playdata (enemies_missed, enemies_hit, buttons_pressed, joystick_moved, start_time, play_time, songinstance) VALUES (" + enemies_missed + ", " + enemies_hit + ", " + buttons_pressed + ", " + joystick_moved + ", NOW(), " + time + ", (SELECT id FROM songinstance WHERE difficulty='" + si.getDifficulty() + "' AND song=(SELECT id FROM song WHERE folder='" + s.getFolder() + "')))";
		//System.out.println(query);
		executeInsertQuery(query);
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
				//System.out.println("Song " + id + " added to database");
				rs.close();
				st.close();
			} catch (SQLException e) {
				System.out.println("Empty return set");
			}
    	
    		if(id != -1) {
    			for(SongInstance si : s.getSongs()) {
	    			Statement st2 = executeResultQuery("SELECT id FROM songinstance WHERE song=" + id + " AND difficulty='" + si.getDifficulty() + "'");
	        		
	        		ResultSet rs2 = null;
	        		
	    			try {
	    				rs2 = st2.getResultSet();
	    				if(!rs2.first()) {
	    					executeInsertQuery("INSERT INTO songinstance (song, enemies, difficulty) VALUES (" + id + ", " + si.getObjects().size() + ", '" + si.getDifficulty() + "');");
	    					//System.out.println("Songinstance Added");
	    				}
	    				rs.close();
	    				st.close();
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    			
	    			Statement st3 = executeResultQuery("SELECT COUNT(1) FROM playdata WHERE songinstance = (SELECT id FROM songinstance WHERE difficulty='" + si.getDifficulty() + "' AND song=(SELECT id FROM song WHERE folder='" + s.getFolder() + "'))");
	    			try {
						ResultSet rs3 = st3.getResultSet();
						rs3.next();
						int i = rs3.getInt(1);
		    			si.setTimesPlayed(i);
		    			System.out.println(s.getTitle() + " - " + si.getDifficulty() + ": " + i);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    			}
    		}
 
    	}
    }
    
    private int executeInsertQuery(String query)
    {
        try{
            Statement s = myConn.createStatement();
            s.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = s.getGeneratedKeys();

            int id = 0;
            if(rs.next())
            	id = rs.getInt(1);

            s.close();
            return id;
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
        return -1;
    }

    //Execute a custom query
    private Statement executeResultQuery(String query)
    {   
        Statement s = null;
        try {
            s = myConn.createStatement();
            s.executeQuery(query);
        }
        catch( SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex) {
           System.out.println("getMeasurement: " + ex.getMessage());
        }
        
        return s;
    }
    
    //Close connection with Database
    @Override
    public void finalize() throws Throwable
    {
        // Close database connection
        if( myConn != null ) {
            try {
                myConn.close();
                System.out.println("Database connection terminated");
            }
            catch( Exception e ) {}
        }

        super.finalize();
    }

}