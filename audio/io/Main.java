package audio.io;

import java.io.File;
import java.io.IOException;

import audio.Song;

public class Main {

	public static void main(String[] args) {
		Song s = null;
		try {
			s = JSONReader.readSong(new File("C:\\Users\\Kenneth\\Development\\Java Development\\SimApplicatie\\Arcade\\songs\\NAME_OF_SONG\\NAME_OF_SONG.csf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(s);
	}

}
