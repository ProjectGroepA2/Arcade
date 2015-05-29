package audio.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import audio.Song;

public class DirScanner {
	
	public static List<Song> scanDirectories(File f)
	{
		if(!f.isDirectory())
			return null;
		
		List<Song> songs = new ArrayList<Song>();
		
		FileFilter dirs = new FileFilter()
		{
			public boolean accept(File e) {
				if(e.isDirectory())
					return true;
				else
					return false;
			}
		};
		FileFilter csf = new FileFilter()
		{
			public boolean accept(File e) {
				if(e.isFile() && e.getName().endsWith(".csf"))
					return true;
				else
					return false;
			}
		};
		
		for(File file : f.listFiles(dirs))
		{
			for(File file2 : file.listFiles(csf))
			{
					try {
						Song s = JSONReader.readSong(file2);
						songs.add(s);
					} catch (IOException e1) {
					}
					
			}
		}
		
		return songs;
	}
}
