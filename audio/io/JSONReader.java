package audio.io;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import audio.ObjectInstance;
import audio.Song;
import audio.SongInstance;

public class JSONReader {

	public static Song readSong(File f) throws IOException {

		if(!f.exists())
			throw new FileNotFoundException("CSF File does not exist");
		
		// Get CSF File
		InputStream is = new FileInputStream(f);

		// Read CSF Content
		JsonReader rdr = Json.createReader(is);
		JsonObject obj = rdr.readObject();
		rdr.close();
		
		if(!obj.containsKey("meta") || !obj.containsKey("file") || !obj.containsKey("data"))
			throw new IOException("Corrupt CSF File");

		// Create new Song
		Song s = new Song();

		// Read META data
		JsonObject meta = obj.getJsonObject("meta");

		s.setTitle(meta.getString("title"));
		s.setSubtitle(meta.getString("subtitle"));
		s.setAuthor(meta.getString("author"));
		s.setCreator(meta.getString("creator"));
		s.setBPM(meta.getInt("BPM"));
		
		//Read FILE data
		JsonObject file = obj.getJsonObject("file");
		
		File audio = new File(file.getString("audio"));
		if(!audio.exists() || !audio.getName().endsWith(".mp3"))
			throw new FileNotFoundException("Audio file does not exist");
		s.setAudio(audio);
		
		File background = new File(file.getString("background"));
		if(!background.exists() || !(background.getName().endsWith(".jpg") || background.getName().endsWith(".png")))
			throw new FileNotFoundException("Background image does not exist");
		s.setBackground(background);

		File banner = new File(file.getString("banner"));
		if(!banner.exists() || !(banner.getName().endsWith(".jpg") || banner.getName().endsWith(".png")))
			throw new FileNotFoundException("Banner image does not exist");
		s.setBanner(banner);
		
		s.setFile(f);
		
		//Read Data data
		JsonArray data = obj.getJsonArray("data");
		for(int i = 0; i < data.size(); i++)
		{
			s.addSongInstance( readSongInstance(data.getJsonObject(i)) );
		}
		
		return s;
	}

	private static SongInstance readSongInstance(JsonObject obj) throws IOException {
		String difficulty = obj.getString("difficulty");
		SongInstance si = new SongInstance(difficulty);
		
		JsonArray object = obj.getJsonArray("objects");
		for(int i = 0; i < object.size(); i++)
		{
			si.addObjectInstance( readObjectInstance(object.getJsonObject(i)) );
		}
		
		return si;
	}
	
	private static ObjectInstance readObjectInstance(JsonObject obj) throws IOException
	{
		ObjectInstance oi = new ObjectInstance();
		
		oi.setTime(obj.getInt("time"));
		oi.setDirection(obj.getInt("direction"));
		
		Color color;
		try {
		    Field field = Color.class.getField(obj.getString("color").toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    throw new IOException("Cannot read property color from " + obj);
		}
		oi.setColor(color);
		
		if(obj.containsKey("hold") && obj.getBoolean("hold"))
		{
			oi.setHold(obj.getBoolean("hold"));
			oi.setLength(obj.getInt("length"));
		}
		
		return oi;
	}
}
