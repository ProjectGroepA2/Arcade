package audio.io;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import model.GameModel;
import audio.ButtonInstance;
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
		is.close();
		
		if(!obj.containsKey("meta") || !obj.containsKey("file") || !obj.containsKey("data"))
			throw new IOException("Corrupt CSF File");

		// Create new Song
		Song s = new Song();

		// Read META data
		JsonObject meta = obj.getJsonObject("meta");

		s.setTitle(meta.getString("title"));
		s.setSubtitle(meta.getString("subtitle"));
		s.setAuthor(meta.getString("author"));
		s.setSampleStart(meta.getInt("sample_start"));
		s.setBPM(meta.getInt("BPM"));
		
		//Read FILE data
		JsonObject file = obj.getJsonObject("file");
		
		File audio = new File(f.getParent() + File.separator + file.getString("audio"));
		if(!audio.exists() || !audio.getName().toLowerCase().endsWith(".mp3"))
			throw new FileNotFoundException("Audio file does not exist: " + audio.getPath());
		s.setAudio(audio);
		
		/*
		File background = new File(f.getParent() + File.separator + file.getString("background"));
		if(!background.exists() || !(background.getName().toLowerCase().endsWith(".jpg") || background.getName().toLowerCase().endsWith(".png")))
			throw new FileNotFoundException("Background image does not exist: " + background.getPath());
		s.setBackground(background);*/
		s.setBackground(null);

		/*
		File banner = new File(f.getParent() + File.separator + file.getString("banner"));
		if(!banner.exists() || !(banner.getName().toLowerCase().endsWith(".jpg") || banner.getName().toLowerCase().endsWith(".png")))
			throw new FileNotFoundException("Banner image does not exist: " + banner.getPath());
		s.setBanner(banner);*/
		s.setBanner(null);
		
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
		
		JsonArray buttons = obj.getJsonArray("buttons");
		for(int i = 0; i < buttons.size(); i++)
		{
			si.addButtonInstance( readButtonInstance(buttons.getJsonObject(i)) );
		}
		
		return si;
	}

	private static ObjectInstance readObjectInstance(JsonObject obj)
	{
		ObjectInstance oi = new ObjectInstance();
		
		oi.setTime(obj.getInt("time"));
		oi.setDirection(obj.getInt("direction"));
		oi.setButtonID(obj.getInt("button"));
		
		if(obj.containsKey("hold") && obj.getBoolean("hold"))
		{
			oi.setHold(obj.getBoolean("hold"));
			oi.setLength(obj.getInt("length"));
		}
		
		return oi;
	}
	
	private static ButtonInstance readButtonInstance(JsonObject obj)
	{
		ButtonInstance bi = new ButtonInstance();
		
		bi.setTime(obj.getInt("time"));
		bi.setButtonID(obj.getInt("button"));
		
		Color color = GameModel.colors[obj.getInt("color") % GameModel.colors.length];
		bi.setColor(color);
		
		return bi;
	}
}
