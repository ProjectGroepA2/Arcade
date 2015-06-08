package audio.sorting;

import java.util.Comparator;

import audio.Song;

public class SortALPHA implements Comparator<Song> {

	@Override
	public int compare(Song s1, Song s2) {
		return s1.getTitle().compareTo(s2.getTitle());
	}

}
